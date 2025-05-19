package com.lms.service;

import com.lms.dto.LoanStatusResponse;

public interface LoanStatusService {
    LoanStatusResponse getLoanStatus(String customerNumber);
    LoanStatusResponse getLoanStatusByApplicationId(String customerNumber, String loanApplicationId);
}

