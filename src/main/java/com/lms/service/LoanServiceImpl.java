package com.lms.service;
import com.lms.dto.CustomerResponseDTO;
import com.lms.dto.LoanRequest;
import com.lms.dto.LoanResponseDTO;
import com.lms.dto.SubscriptionResponse;
import com.lms.model.Customer;
import com.lms.model.Loan;
import com.lms.repository.LoanRepository;

import jakarta.transaction.Transactional;

import com.credable.credable.soap.generated.CustomerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final CustomerService customerService;
    private final LoanRepository loanRepository;
    private final ScoringService scoringService;

    @Override
    @Transactional
    public SubscriptionResponse subscribeCustomer(String customerNumber) {
        log.info("Processing subscription request for customer: {}", customerNumber);
        
        try {
            // Fetch customer from SOAP API
            CustomerResponseDTO customerResponse = customerService.getCustomerDetails(customerNumber);
            
            // Save or update customer in our database
            Customer customer = customerService.findOrCreateCustomer(customerResponse);
            
            // Check if customer has any ongoing loan
            boolean hasOngoingLoan = hasOngoingLoan(customerNumber);
            
            return new SubscriptionResponse(
                    customer.getCustomerNumber(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    hasOngoingLoan ? "Customer has ongoing loan application" : "Customer is eligible for loan",
                    !hasOngoingLoan
            );
            
        } catch (Exception e) {
            log.error("Error processing subscription request", e);
            return new SubscriptionResponse(
                    customerNumber,
                    null,
                    null,
                    "Failed to process subscription: " + e.getMessage(),
                    false
            );
        }
    }

@Transactional
public LoanResponseDTO processLoanRequest(LoanRequest loanRequestDTO) {
    log.info("Processing loan request for customer: {}", loanRequestDTO.getCustomerNumber());
    
    String customerNumber = loanRequestDTO.getCustomerNumber();
    
    // Check if customer has ongoing loan
    if (hasOngoingLoan(customerNumber)) {
        log.warn("Customer {} has ongoing loan", customerNumber);
        return new LoanResponseDTO(
        );
    }
    
    try {
        // Get customer details
        Optional<Customer> customerOpt = customerService.findByCustomerNumber(customerNumber);
        
        if (customerOpt.isEmpty()) {
            // Try to fetch from SOAP API
            CustomerResponseDTO customerResponse = customerService.getCustomerDetails(customerNumber);
            Customer customer = customerService.findOrCreateCustomer(customerResponse);
            customerOpt = Optional.of(customer);
        }
        
        Customer customer = customerOpt.get();
        
        // Create loan record
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setAmount(loanRequestDTO.getAmount());
        loan.setStatus(Loan.LoanStatus.PENDING);
        
        Loan savedLoan = loanRepository.save(loan);
        
        // Process loan asynchronously
        processLoanApplication(savedLoan);
        
        return LoanResponseDTO.fromLoan(savedLoan);
        
    } catch (Exception e) {
        log.error("Error processing loan request", e);
        return new LoanResponseDTO(
        );
    }
}

    @Override
    public LoanResponseDTO getLoanStatus(String CustomerNumber) {
        log.info("Checking loan status for reference: {}", CustomerNumber);
        
       List<Loan> loans = loanRepository.findByCustomerNumber(CustomerNumber);
        if (!loans.isEmpty()) {
            return LoanResponseDTO.fromLoan(loans.get(0));
        } else {
            return new LoanResponseDTO(
                    CustomerNumber,
            null,
            null,
            "UNKNOWN",
            null,
            "Customer Number  not found"
    );
        }   
}

    @Override
    @Async
    @Transactional
    public void processLoanApplication(Loan loan) {
        log.info("Processing loan application for Customer Number: {}", loan.getCustomerNumber());
        
        try {
            // Step 1: Initiate scoring
            String token = scoringService.initiateScoring(loan.getCustomerNumber());
            loan.setToken(token);
            loanRepository.save(loan);
            
            // Step 2: Query score with retry mechanism
            scoringService.processScoringWithRetry(loan);
            
        } catch (Exception e) {
            log.error("Error processing loan application", e);
            loan.setStatus(Loan.LoanStatus.FAILED);
            loanRepository.save(loan);
        }
    }

    @Override
    public boolean hasOngoingLoan(String customerNumber) {
        Optional<Customer> customerOpt = customerService.findByCustomerNumber(customerNumber);
        
        if (customerOpt.isEmpty()) {
            return false;
        }
        
        List<Loan.LoanStatus> ongoingStatuses = Arrays.asList(Loan.LoanStatus.PENDING);
        return loanRepository.existsByCustomerNumberAndStatusIn(customerOpt.get(), ongoingStatuses);
    }
    
    // private String generateReferenceNumber() {
    //     return "LN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    // }

    @Override
    public LoanResponseDTO requestLoan(LoanRequest loanRequestDTO) {
        return processLoanRequest(loanRequestDTO);
    }
}

