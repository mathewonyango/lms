package com.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lms.model.Loan;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {
    private String referenceNumber;
    private String customerNumber;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;
    private String message;

    public static LoanResponseDTO fromLoan(Loan loan) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setReferenceNumber(loan.getCustomerNumber());
        dto.setCustomerNumber(loan.getCustomerNumber());
        // dto.setApprovedAmount(loan.getApprovedAmount() != null ? 
        
            // new BigDecimal(loan.getApprovedAmount()) : BigDecimal.ZERO);
        dto.setStatus(loan.getStatus().name());
        dto.setCreatedAt(loan.getCreatedAt());
        
        String message = "";
        switch (loan.getStatus()) {
            case APPROVED:
                message = "Loan approved successfully";
                break;
            case REJECTED:
                message = "Loan request rejected: " + 
                    (loan.getExclusionReason() != null ? loan.getExclusionReason() : "");
                break;
            case FAILED:
                message = "Loan processing failed";
                break;
            case PENDING:
                message = "Loan is being processed";
                break;
        }
        dto.setMessage(message);
        
        return dto;
    }

    private void setApprovedAmount(BigDecimal approvedAmount) {
        this.amount = approvedAmount;
    }
}

