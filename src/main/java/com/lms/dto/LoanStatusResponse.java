package com.lms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatusResponse {
    @JsonProperty("customerNumber")
    private String customerNumber;
    
    @JsonProperty("totalActiveLoans")
    private Integer totalActiveLoans;
    
    @JsonProperty("totalLoanApplications")
    private Integer totalLoanApplications;
    
    @JsonProperty("loans")
    private List<LoanStatus> loans;
}
