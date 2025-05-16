package com.lms.service;
import com.lms.dto.ScoreResponseDTO;
import com.lms.model.Loan;
import com.lms.model.ScoringClient;

public interface ScoringService {
    ScoringClient registerClient();
    String initiateScoring(String customerNumber);
    void processScoringWithRetry(Loan loan);
     String initiateScoreQuery(String customerNumber);
    ScoreResponseDTO pollScore(String token);
}