package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.dto.ScoreDTO;
import com.lms.dto.ScoreDetailsResponse;
import com.lms.model.TransactionDetailsList;
import com.lms.service.ScoringService;
import com.lms.service.TransactionService;

import jakarta.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scoring")
public class ScoringController {

    @Autowired
    private ScoringService scoringService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/initiateQueryScore/{customerNumber}")
    public String initiateScore(@PathVariable String customerNumber) {
        return scoringService.initiateScore(customerNumber);
    }

    @GetMapping("/queryScore/{token}")
    public ScoreDetailsResponse getScore(@PathVariable String token) {
        return scoringService.fetchScore(token);
    }

    @GetMapping("/customer/{customerNumber}")
    public ResponseEntity<ApiResponse<ScoreDTO>> getCustomerScore(@PathVariable String customerNumber) {
        try {
            if (customerNumber == null || customerNumber.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Customer number is required", null));
            }

            TransactionDetailsList transactionData = transactionService.getTransactionData(customerNumber);

            if (transactionData.isEmpty() || transactionData.getTransactions().isEmpty()) {
                return ResponseEntity.ok()
                    .body(new ApiResponse<>(true, "No transactions found", null));
            }

            ScoreDTO score = scoringService.calculateScore(customerNumber, transactionData.getTransactions());

            return ResponseEntity.ok()
                .body(new ApiResponse<>(true, "Score calculated successfully", score));

        } catch (JAXBException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error processing transaction data: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Unexpected error: " + e.getMessage(), null));
        }
    }
}
