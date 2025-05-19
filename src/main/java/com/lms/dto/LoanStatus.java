package com.lms.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatus {
    @JsonProperty("loanApplicationId")
    private String loanApplicationId;
    
    @JsonProperty("customerNumber")
    private String customerNumber;
    
    @JsonProperty("currentStatus")
    private String currentStatus;
    
    @JsonProperty("statusDescription")
    private String statusDescription;
    
    @JsonProperty("requestedAmount")
    private Double requestedAmount;
    
    @JsonProperty("approvedAmount")
    private Double approvedAmount;
    
    @JsonProperty("disbursedAmount")
    private Double disbursedAmount;
    
    @JsonProperty("applicationDate")
    private LocalDateTime applicationDate;
    
    @JsonProperty("approvalDate")
    private LocalDateTime approvalDate;
    
    @JsonProperty("disbursementDate")
    private LocalDateTime disbursementDate;
    
    @JsonProperty("expectedDisbursementDate")
    private LocalDateTime expectedDisbursementDate;
    
    @JsonProperty("interestRate")
    private Double interestRate;
    
    @JsonProperty("repaymentPeriod")
    private Integer repaymentPeriod;
    
    @JsonProperty("monthlyRepayment")
    private Double monthlyRepayment;
    
    @JsonProperty("nextPaymentDate")
    private LocalDateTime nextPaymentDate;
    
    @JsonProperty("outstandingBalance")
    private Double outstandingBalance;
    
    @JsonProperty("totalRepaid")
    private Double totalRepaid;
    
    @JsonProperty("statusHistory")
    private List<LoanStatusHistory> statusHistory;
}