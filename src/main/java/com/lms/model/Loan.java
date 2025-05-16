package com.lms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String customerNumber;
    
    private Double requestedAmount;
    private String approvedAmount;
    //token
    private  String token;
    
    // Add @Enumerated(EnumType.STRING) to persist enum as a string
    // @Enumerated(EnumType.STRING)
    private LoanStatus status;
    
    
    private Integer score;
    private Double scoreLimit;
    private String exclusion;
    private String exclusionReason;
    
    private String rejectionReason;
    
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
    private LocalDateTime disbursementDate;
    private LocalDateTime dueDate;
    private LocalDateTime closureDate;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.applicationDate == null) {
            this.applicationDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public enum LoanStatus {
        PENDING,
        PROCESSING,
        APPROVED,
        REJECTED,
        DISBURSED,
        CLOSED, FAILED
    }

    // public void setLoanReferenceNumber(String referenceNumber) {
    //     this.loanReferenceNumber = referenceNumber;
    // }

    public void setAmount(Double amount) {
        this.requestedAmount = amount;
    }

    public void setCustomer(Customer customer) {
        this.customerNumber = customer.getCustomerNumber();
    }

    public void setRetryCount(int attemptCount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRetryCount'");
    }

    public void approvedAmount(BigDecimal limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'approvedAmount'");
    }
}
