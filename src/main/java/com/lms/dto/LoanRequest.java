package com.lms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    
    @NotBlank(message = "Customer number is required")
    private String customerNumber;
    
    @NotNull(message = "Amount is required")
     @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;
}