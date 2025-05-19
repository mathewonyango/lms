package com.lms.client;

import com.lms.dto.ScoreDetailsResponse;
import com.lms.dto.ScoreTokenResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScoringClient {

    @Value("${scoring.api.base-url}")
    private String baseUrl;

    @Value("${scoring.api.client-token}")
    private String clientToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public ScoreTokenResponse initiateScoreQuery(String customerNumber) {
        String url = baseUrl + "/initiateQueryScore/" + customerNumber;

        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token", clientToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<ScoreTokenResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, request, ScoreTokenResponse.class);

        return response.getBody();
    }

    public ScoreDetailsResponse getScoreDetails(String token) {
        String url = baseUrl + "/queryScore/" + token;

        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token", clientToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<ScoreDetailsResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, request, ScoreDetailsResponse.class);

        return response.getBody();
    }
}
