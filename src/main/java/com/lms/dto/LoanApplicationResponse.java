package com.lms.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponse {
    @JsonProperty("loanApplicationId")
    private String loanApplicationId;
    
    @JsonProperty("customerNumber")
    private String customerNumber;
    
    @JsonProperty("requestedAmount")
    private Double requestedAmount;
    
    @JsonProperty("approvedAmount")
    private Double approvedAmount;
    
    @JsonProperty("applicationStatus")
    private String applicationStatus; // PENDING, APPROVED, REJECTED, PROCESSING
    
    @JsonProperty("applicationDate")
    private LocalDateTime applicationDate;
    
    @JsonProperty("interestRate")
    private Double interestRate;
    
    @JsonProperty("repaymentPeriod")
    private Integer repaymentPeriod; // in months
    
    @JsonProperty("monthlyRepayment")
    private Double monthlyRepayment;
    
    @JsonProperty("totalRepayment")
    private Double totalRepayment;
    
    @JsonProperty("rejectionReason")
    private String rejectionReason;
    
    @JsonProperty("nextAction")
    private String nextAction;
    
    @JsonProperty("message")
    private String message;
}