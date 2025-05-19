package com.lms.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.dto.LoanRequestDTO;
import com.lms.dto.LoanResponseDTO;
import com.lms.model.LoanApplication;
import com.lms.model.ScoreEntity;
import com.lms.repository.LoanApplicationRepository;
import com.lms.repository.ScoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService {

    // private final ScoringService scoringService; // Seems unused – consider removing if unnecessary
    private final LoanApplicationRepository loanRepo;
    private final ScoreRepository scoreRepository;

    public LoanResponseDTO applyForLoan(LoanRequestDTO request) {
        Optional<ScoreEntity> optionalScore = scoreRepository
                .findTopByCustomerNumberOrderByCreatedAtDesc(request.getCustomerNumber());

        if (optionalScore.isEmpty()) {
            return LoanResponseDTO.builder()
                    .approved(false)
                    .customerNumber(request.getCustomerNumber())
                    .approvedAmount(0.0)
                    .message("Loan denied: No scoring data found for customer. Please subscribe to our service first to get your credit score and eligibility.")
                    .approvalDate(LocalDateTime.now())
                    .build();
        }

        ScoreEntity scoreEntity = optionalScore.get();

        // Handle null limitAmount safely
        Double limitAmount = scoreEntity.getLimitAmount();
        if (limitAmount == null) {
            return LoanResponseDTO.builder()
                    .approved(false)
                    .customerNumber(request.getCustomerNumber())
                    .approvedAmount(0.0)
                    .message("Loan denied: Credit limit not available. Please update your scoring information.")
                    .approvalDate(LocalDateTime.now())
                    .build();
        }

        boolean isExcluded = Boolean.TRUE.equals(scoreEntity.getIsExcluded());
        boolean isEligible = !isExcluded && limitAmount >= request.getRequestedAmount();

        LoanApplication loan = LoanApplication.builder()
                .customerNumber(request.getCustomerNumber())
                .requestedAmount(request.getRequestedAmount())
                .createdAt(LocalDateTime.now())
                .approved(isEligible)
                .approvedAmount(isEligible ? request.getRequestedAmount() : 0.0)
                .rejectionReason(isEligible ? null : buildRejectionReason(scoreEntity, request.getRequestedAmount()))
                .build();

        loanRepo.save(loan);

        return LoanResponseDTO.builder()
                .approved(isEligible)
                .customerNumber(loan.getCustomerNumber())
                .approvedAmount(loan.getApprovedAmount())
                .message(isEligible ? "Loan approved" : "Loan denied: " + loan.getRejectionReason())
                .approvalDate(loan.getCreatedAt())
                .build();
    }

    private String buildRejectionReason(ScoreEntity score, double requestedAmount) {
        if (Boolean.TRUE.equals(score.getIsExcluded())) {
            return "Customer excluded due to: " + score.getExclusionReason();
        }
        
        Double limitAmount = score.getLimitAmount();
        if (limitAmount == null) {
            return "Credit limit not available";
        }
        
        return "Requested amount (" + requestedAmount + ") exceeds approved limit of " + limitAmount;
    }
}