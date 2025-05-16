package com.lms.service;


import com.lms.dto.LoanRequest;
import com.lms.dto.LoanResponseDTO;
import com.lms.dto.SubscriptionResponse;
import com.lms.model.Loan;

public interface LoanService {
    SubscriptionResponse subscribeCustomer(String customerNumber);
    LoanResponseDTO requestLoan(LoanRequest loanRequestDTO);
    LoanResponseDTO getLoanStatus(String referenceNumber);
    
    void processLoanApplication(Loan loan);
    boolean hasOngoingLoan(String customerNumber);
}
