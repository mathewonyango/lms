package com.lms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor

public class TransactionController {

    private final TransactionService transactionService;
    
    @GetMapping("/{customerNumber}")
    public ResponseEntity<List<Object>> getTransactionData(@PathVariable String customerNumber) {
        log.info("Fetching transaction data for customer: {}", customerNumber);
        
        try {
            List<Object> transactionData = transactionService.getTransactionData(customerNumber);
            return ResponseEntity.ok(transactionData);
        } catch (Exception e) {
            log.error("Error fetching transaction data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}