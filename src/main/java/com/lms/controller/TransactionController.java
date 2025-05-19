package com.lms.controller;

import com.lms.dto.ApiResponse;
import com.lms.dto.TransactionRequest;
import com.lms.dto.TransactionStats;
import com.lms.model.TransactionDetailsDTO;
import com.lms.model.TransactionDetailsList;
import com.lms.service.TransactionService;

import jakarta.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*") // Configure as needed for your frontend
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    /**
     * Get transaction data for a specific customer
     * @param customerNumber The customer number to fetch transactions for
     * @return ResponseEntity containing transaction details or error message
     */
    @GetMapping("/customer/{customerNumber}")
    public ResponseEntity<ApiResponse<TransactionDetailsList>> getTransactionsByCustomer(
            @PathVariable String customerNumber) {
        
        try {
            // Validate customer number
            if (customerNumber == null || customerNumber.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Customer number is required", null));
            }
            
            // Get transaction data
            TransactionDetailsList transactionData = transactionService.getTransactionData(customerNumber);
            
            // Check if transactions were found
            if (transactionData.isEmpty()) {
                return ResponseEntity.ok()
                    .body(new ApiResponse<>(true, "No transactions found for customer: " + customerNumber, transactionData));
            }
            
            // Return successful response
            return ResponseEntity.ok()
                .body(new ApiResponse<>(true, "Transactions retrieved successfully", transactionData));
                
        } catch (JAXBException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error processing transaction data: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Unexpected error occurred: " + e.getMessage(), null));
        }
    }
    
    /**
     * Get transaction data with request body (alternative endpoint)
     * @param request The request containing customer number
     * @return ResponseEntity containing transaction details or error message
     */
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<TransactionDetailsList>> getTransactionsByRequest(
            @RequestBody TransactionRequest request) {
        
        try {
            // Validate request
            if (request == null || request.getCustomerNumber() == null || request.getCustomerNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Customer number is required in request body", null));
            }
            
            // Get transaction data
            TransactionDetailsList transactionData = transactionService.getTransactionData(request.getCustomerNumber());
            
            // Return response
            return ResponseEntity.ok()
                .body(new ApiResponse<>(true, "Transactions retrieved successfully", transactionData));
                
        } catch (JAXBException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error processing transaction data: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Unexpected error occurred: " + e.getMessage(), null));
        }
    }
    
    /**
     * Health check endpoint for the transaction service
     * @return ResponseEntity indicating service health
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok()
            .body(new ApiResponse<>(true, "Transaction service is healthy", "OK"));
    }
    
    /**
     * Get transaction statistics for a customer (example of additional endpoint)
     * @param customerNumber The customer number
     * @return ResponseEntity containing transaction statistics
     */
    @GetMapping("/customer/{customerNumber}/stats")
    public ResponseEntity<ApiResponse<TransactionStats>> getTransactionStats(
            @PathVariable String customerNumber) {
        
        try {
            if (customerNumber == null || customerNumber.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Customer number is required", null));
            }
            
            // Get transaction data
            TransactionDetailsList transactionData = transactionService.getTransactionData(customerNumber);
            
            // Calculate statistics
            TransactionStats stats = calculateTransactionStats(transactionData);
            
            return ResponseEntity.ok()
                .body(new ApiResponse<>(true, "Transaction statistics calculated successfully", stats));
                
        } catch (JAXBException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error processing transaction data: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Unexpected error occurred: " + e.getMessage(), null));
        }
    }
    
    /**
     * Calculate transaction statistics from transaction data
     * @param transactionData The transaction data
     * @return TransactionStats object
     */
    private TransactionStats calculateTransactionStats(TransactionDetailsList transactionData) {
        TransactionStats stats = new TransactionStats();
        
        if (transactionData.isEmpty()) {
            return stats;
        }
        
        double totalCreditAmount = 0.0;
        double totalDebitAmount = 0.0;
        long totalTransactionCount = 0;
        
        // Calculate totals across all transactions
        for (var transaction : transactionData.getTransactions()) {
            if (transaction.getCredittransactionsAmount() != null) {
                totalCreditAmount += transaction.getCredittransactionsAmount();
            }
            if (transaction.getMonthlydebittransactionsAmount() != null) {
                totalDebitAmount += transaction.getMonthlydebittransactionsAmount();
            }
            if (transaction.getAtmTransactionsNumber() != null) {
                totalTransactionCount += transaction.getAtmTransactionsNumber();
            }
        }
        
        stats.setTotalCreditAmount(totalCreditAmount);
        stats.setTotalDebitAmount(totalDebitAmount);
        stats.setTotalTransactionCount(totalTransactionCount);
        stats.setNetAmount(totalCreditAmount - totalDebitAmount);
        stats.setTransactionRecordCount(transactionData.getTotalCount());
        
        return stats;
    }
}