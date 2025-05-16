package com.lms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.LoanResponseDTO;
import com.lms.service.LoanService;
//subscriptionResponse
import com.lms.dto.SubscriptionResponse;
import com.lms.dto.LoanRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    
    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionResponse> subscribeCustomer(@RequestParam String customerNumber) {
        log.info("Received subscription request for customer: {}", customerNumber);
        SubscriptionResponse response = loanService.subscribeCustomer(customerNumber);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/request")
    public ResponseEntity<LoanResponseDTO> requestLoan(@Valid @RequestBody LoanRequest loanRequestDTO) {
        log.info("Received loan request: {}", loanRequestDTO);
        LoanResponseDTO response = loanService.requestLoan(loanRequestDTO);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{CustomerNumber}")
    public ResponseEntity<LoanResponseDTO> getLoanStatus(@PathVariable String CustomerNumber) {
        log.info("Checking loan status for reference: {}", CustomerNumber);
        LoanResponseDTO response = loanService.getLoanStatus(CustomerNumber);
        return ResponseEntity.ok(response);
    }
}
