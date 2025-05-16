package com.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
    
    private Long loanId;
    private String customerNumber;
    private String customerName;
    private Double requestedAmount;
    private Double approvedAmount;
    private String status;
    private LocalDateTime applicationDate;
    private String message;
  
    private Double Amount;
}