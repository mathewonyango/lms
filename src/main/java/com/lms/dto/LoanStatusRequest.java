package com.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatusRequest {
    @JsonProperty("customerNumber")
    private String customerNumber;
    
    @JsonProperty("loanApplicationId")
    private String loanApplicationId; // Optional: specific loan ID
}