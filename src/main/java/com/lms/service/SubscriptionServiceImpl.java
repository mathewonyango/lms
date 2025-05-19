package com.lms.service;

import com.lms.dto.*;
import com.lms.model.TransactionDetailsList;
import com.lms.service.ScoringService;
import com.lms.service.SubscriptionService;
import com.lms.service.TransactionService;

import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final TransactionService transactionService;
    private final ScoringService scoringService;

    public SubscriptionResponse subscribeCustomer(SubscriptionRequest request) {
        String customerNumber = request.getCustomerNumber();
        
        try {
            // Step 1: Get transaction data
            TransactionDetailsList transactionData = transactionService.getTransactionData(customerNumber);

            if (transactionData == null || transactionData.isEmpty()) {
                return SubscriptionResponse.builder()
                    .customerNumber(customerNumber)
                    .isEligible(false)
                    .subscriptionStatus("INACTIVE")
                    .eligibilityScore(0.0)
                    .maxLoanAmount(0.0)
                    .subscriptionDate(LocalDateTime.now())
                    .eligibilityReasons(List.of("No transaction history found"))
                    .message("Customer is not eligible due to missing transaction history")
                    .build();
            }

            // Step 2: Calculate internal score and save to database
            ScoreDTO internalScore = scoringService.calculateScore(customerNumber, transactionData.getTransactions());
            log.info("Internal scoring completed for customer: {}", customerNumber);

            // Step 3: Try to also get external scoring (optional - for comparison/backup)
            ScoreDetailsResponse externalScore = null;
            try {
                String token = scoringService.initiateScore(customerNumber);
                if (token != null) {
                    externalScore = scoringService.fetchScore(token);
                    log.info("External scoring completed for customer: {}", customerNumber);
                }
            } catch (Exception e) {
                log.warn("External scoring failed for customer: {} - Error: {}", customerNumber, e.getMessage());
                // Continue with internal scoring only
            }

            // Step 4: Build response based on internal scoring results
            // Since the internal scoring already saved the ScoreEntity to the database,
            // the loan application will now find the scoring data
            return SubscriptionResponse.builder()
                .customerNumber(customerNumber)
                .isEligible(!internalScore.isExcluded())
                .subscriptionStatus(internalScore.isExcluded() ? "INACTIVE" : "ACTIVE")
                .maxLoanAmount(internalScore.getLimitAmount())
                .accountNumber(customerNumber)
                .subscriptionDate(LocalDateTime.now())
                .eligibilityReasons(internalScore.isExcluded() ? 
                    List.of(internalScore.getExclusionReason()) : 
                    List.of("Customer meets eligibility criteria"))
                .message(internalScore.isExcluded() ? 
                    "Customer excluded: " + internalScore.getExclusionReason() : 
                    "Customer successfully subscribed and scored")
                .build();

        } catch (JAXBException e) {
            log.error("JAXB error processing transaction data for customer: {}", customerNumber, e);
            return SubscriptionResponse.builder()
                .customerNumber(customerNumber)
                .isEligible(false)
                .subscriptionStatus("ERROR")
                .eligibilityScore(0.0)
                .maxLoanAmount(0.0)
                .subscriptionDate(LocalDateTime.now())
                .eligibilityReasons(List.of("Error processing transaction data"))
                .message("Error occurred while processing subscription request")
                .build();
        } catch (Exception e) {
            log.error("Unexpected error during subscription for customer: {}", customerNumber, e);
            return SubscriptionResponse.builder()
                .customerNumber(customerNumber)
                .isEligible(false)
                .subscriptionStatus("ERROR")
                .eligibilityScore(0.0)
                .maxLoanAmount(0.0)
                .subscriptionDate(LocalDateTime.now())
                .eligibilityReasons(List.of("Unexpected error occurred"))
                .message("Error occurred while processing subscription request")
                .build();
        }
    }
}