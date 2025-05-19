package com.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lms.model.Loan;

@Data
@Builder
public class LoanResponseDTO {
    private boolean approved;
    private String message;
    private Double approvedAmount;
    private String customerNumber;
    private LocalDateTime approvalDate;
}