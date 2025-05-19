package com.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    @JsonProperty("customerNumber")
    private String customerNumber;
    
    @JsonProperty("isEligible")
    private Boolean isEligible;
    
    @JsonProperty("subscriptionStatus")
    private String subscriptionStatus; // ACTIVE, INACTIVE, PENDING, SUSPENDED
    
    @JsonProperty("eligibilityScore")
    private Double eligibilityScore;
    
    @JsonProperty("maxLoanAmount")
    private Double maxLoanAmount;
    
    @JsonProperty("customerName")
    private String customerName;
    
    @JsonProperty("accountNumber")
    private String accountNumber;
    
    @JsonProperty("subscriptionDate")
    private LocalDateTime subscriptionDate;
    
    @JsonProperty("eligibilityReasons")
    private List<String> eligibilityReasons;
    
    @JsonProperty("message")
    private String message;
}


