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
public class SubscriptionResponse {
    
    public SubscriptionResponse(String customerNumber, String firstName, String lastName, Object object, boolean b) {
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.message = object.toString();
        this.subscriptionDate = LocalDateTime.now();
        this.expiryDate = b ? LocalDateTime.now().plusYears(1) : null;
        this.status = b ? "ACTIVE" : "INACTIVE";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    private String customerNumber;
    private String customerName;
    private String status;
    private LocalDateTime subscriptionDate;
    private LocalDateTime expiryDate;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String loanStatus;
    private String firstName;
    private String lastName;

}

