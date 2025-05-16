package com.lms.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.dto.ScoreResponseDTO;
import com.lms.model.Loan;
import com.lms.model.ScoringClient;
import com.lms.repository.LoanRepository;
import com.lms.repository.ScoringClientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    private final ScoringClientRepository scoringClientRepository;
    private final LoanRepository loanRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${scoring.base-url}")
    private String scoringBaseUrl;

    @Value("${scoring.client-token}")
    private String clientToken;

    @Value("${scoring.client.username}")
    private String scoringUsername;

    @Value("${scoring.client.password}")
    private String scoringPassword;

    @Value("${scoring.client.callback-url}")
    private String callbackUrl;

    @Value("${scoring.register.endpoint}")
    private String registerEndpoint;

    @Value("${scoring.initiate.endpoint}")
    private String initiateEndpoint;

    @Value("${scoring.query.endpoint}")
    private String queryEndpoint;

    @Value("${scoring.retry.max-attempts}")
    private int maxRetryAttempts;

    @Value("${scoring.retry.delay}")
    private long retryDelay;

    private ScoringClient activeClient;

    @PostConstruct
    public void init() {
        Optional<ScoringClient> clientOpt = scoringClientRepository.findByActiveTrue();
        activeClient = clientOpt.orElseGet(this::registerClient);
    }

    private String getFullRegisterUrl() {
        return  registerEndpoint;
    }

    @Override
    @Transactional
    public ScoringClient registerClient() {
        log.info("Registering client with scoring service");

        String url = getFullRegisterUrl();
        String serviceName = "LMS-Service-" + System.currentTimeMillis();

        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("url", callbackUrl);
        requestMap.put("name", serviceName);
        requestMap.put("username", scoringUsername);
        requestMap.put("password", scoringPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);



        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestMap, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode root = objectMapper.readTree(response.getBody());

                ScoringClient client = new ScoringClient();
                client.setClientId(root.get("id").asText());
                client.setName(root.get("name").asText());
                client.setUrl(root.get("url").asText());
                client.setUsername(root.get("username").asText());
                client.setPassword(root.get("password").asText());
                client.setToken(root.get("token").asText());
                client.setActive(true);

                scoringClientRepository.findByActiveTrue()
                        .ifPresent(c -> {
                            c.setActive(false);
                            scoringClientRepository.save(c);
                        });

                return scoringClientRepository.save(client);
            }

            log.error("Failed to register client. Response: {}", response);
            throw new RuntimeException("Failed to register client with scoring service");

        } catch (Exception e) {
            log.error("Error registering client with scoring service", e);
            throw new RuntimeException("Error registering client with scoring service: " + e.getMessage(), e);
        }
    }

    @Override
    public String initiateScoring(String customerNumber) {
        log.info("Initiating scoring for customer: {}", customerNumber);

        if (activeClient == null) {
            throw new RuntimeException("No active scoring client found");
        }

        String url = scoringBaseUrl + initiateEndpoint + "/" + customerNumber;

        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token", activeClient.getToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String token = response.getBody();
                log.info("Successfully initiated scoring. Token: {}", token);
                return token;
            }

            log.error("Failed to initiate scoring. Response: {}", response);
            throw new RuntimeException("Failed to initiate scoring process");

        } catch (Exception e) {
            log.error("Error initiating scoring", e);
            throw new RuntimeException("Error initiating scoring: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void processScoringWithRetry(Loan loan) {
        log.info("Processing scoring with retry for loan: {}", loan.getCustomerNumber());

        if (activeClient == null || loan.getToken() == null) {
            log.error("Missing active client or token");
            loan.setStatus(Loan.LoanStatus.FAILED);
            loanRepository.save(loan);
            return;
        }

        int attemptCount = 0;
        boolean success = false;

        while (attemptCount < maxRetryAttempts && !success) {
            attemptCount++;
            loan.setRetryCount(attemptCount);
            loanRepository.save(loan);

            log.info("Attempt {} to query score for loan: {}", attemptCount, loan.getCustomerNumber());

            try {
                String url = scoringBaseUrl + queryEndpoint + "/" + loan.getToken();

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.set("client-token", activeClient.getToken());

                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    JsonNode root = objectMapper.readTree(response.getBody());

                    int score = root.get("score").asInt();
                    BigDecimal limit = BigDecimal.valueOf(root.get("limitAmount").asDouble());
                    String exclusion = root.get("exclusion").asText();
                    String exclusionReason = root.get("exclusionReason").asText();

                    loan.setScore(score);
                    loan.setApprovedAmount(limit.toPlainString());
                    loan.setExclusion(exclusion);
                    loan.setExclusionReason(exclusionReason);

                    if ("No Exclusion".equalsIgnoreCase(exclusion)) {
                        loan.setStatus(Loan.LoanStatus.APPROVED);
                    } else {
                        loan.setStatus(Loan.LoanStatus.REJECTED);
                    }

                    success = true;
                    log.info("Successfully processed scoring for loan: {}", loan.getCustomerNumber());

                } else {
                    log.warn("Failed to query score: {} - {}", response.getStatusCode(), response.getBody());
                    Thread.sleep(retryDelay);
                }

            } catch (Exception e) {
                log.error("Error querying score", e);
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!success) {
            loan.setStatus(Loan.LoanStatus.FAILED);
            log.error("Failed to process scoring after {} attempts", maxRetryAttempts);
        }

        loanRepository.save(loan);
    }

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    @Transactional
    public void processFailedLoans() {
        log.info("Running scheduled task to process failed loans.");
        List<Loan> failedLoans = loanRepository.findByStatus(Loan.LoanStatus.FAILED);
        failedLoans.forEach(this::processScoringWithRetry);
    }

    public ScoreResponseDTO pollScore(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token", activeClient.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        int retries = 5;
        long delay = 3000;

        for (int i = 0; i < retries; i++) {
            try {
                ResponseEntity<ScoreResponseDTO> response = restTemplate.exchange(
                        scoringBaseUrl + "/scoring/queryScore/" + token,
                        HttpMethod.GET,
                        entity,
                        ScoreResponseDTO.class
                );

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    return response.getBody();
                }

                Thread.sleep(delay);
            } catch (Exception e) {
                log.error("Error polling score", e);
            }
        }

        throw new RuntimeException("Scoring timed out after " + retries + " retries");
    }

    @Override
    public String initiateScoreQuery(String customerNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token", activeClient.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                scoringBaseUrl + "/scoring/initiateQueryScore/" + customerNumber,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to initiate scoring query");
        }
    }
}
