package com.lms.service;

import com.lms.client.ScoringClient;
import com.lms.dto.ScoreDTO;
import com.lms.dto.ScoreDetailsResponse;
import com.lms.dto.ScoreTokenResponse;
import com.lms.model.ScoreEntity;
import com.lms.model.TransactionDetailsDTO;
import com.lms.repository.ScoreRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoringService {

    @Autowired
    private ScoringClient scoringClient;

    @Autowired
    private ScoreRepository scoreRepository;

    public String initiateScore(String customerNumber) {
        ScoreTokenResponse response = scoringClient.initiateScoreQuery(customerNumber);
        return response.getToken();
    }

    public ScoreDetailsResponse fetchScore(String token) {
        // Fetch score details
        ScoreDetailsResponse response = scoringClient.getScoreDetails(token);

        // Map to entity
        ScoreEntity entity = new ScoreEntity();
        entity.setScore(response.getScore());
        entity.setExclusionReason(response.getExclusionReason());
        entity.setLimitAmount((double) response.getLimitAmount());
        entity.setExclusionReason(response.getExclusion());
        // entity.setRemoteId(response.getId());
        entity.setCustomerNumber(response.getCustomerNumber());

        // Persist to database
        scoreRepository.save(entity);

        // Return the response DTO
        return response;
    }

 
public ScoreDTO calculateScore(String customerNumber, List<TransactionDetailsDTO> transactions) {
    double totalCredit = 0;
    double totalDebit = 0;
    double totalBalance = 0;
    int count = transactions.size();

    for (TransactionDetailsDTO txn : transactions) {
        totalCredit += Optional.ofNullable(txn.getCredittransactionsAmount()).orElse(0.0);
        totalDebit += Optional.ofNullable(txn.getMonthlydebittransactionsAmount()).orElse(0.0);
        totalBalance += Optional.ofNullable(txn.getMonthlyBalance()).orElse(0.0);
    }

    double avgCredit = totalCredit / count;
    double avgDebit = totalDebit / count;
    double avgBalance = totalBalance / count;

    double spendingRatio = avgDebit / (avgCredit == 0 ? 1 : avgCredit);
    String expenditureBehavior = spendingRatio > 1.0 ? "Overspending"
            : spendingRatio > 0.7 ? "Moderate spender"
            : "Conservative spender";

    boolean isExcluded = false;
    String exclusionReason = null;
    if (avgCredit < 500) {
        isExcluded = true;
        exclusionReason = "Low income (avg credit < 500)";
    } else if (avgBalance < 100) {
        isExcluded = true;
        exclusionReason = "Low savings (avg balance < 100)";
    } else if (spendingRatio > 1.5) {
        isExcluded = true;
        exclusionReason = "High expenditure behavior";
    }

    double limitAmount = calculateLimit(avgCredit, avgBalance, avgDebit);
    double monthlyLimit = limitAmount;
    double weeklyLimit = limitAmount / 4;

    // Save to DB
    ScoreEntity scoreEntity = ScoreEntity.builder()
            .customerNumber(customerNumber)
            .averageMonthlyCredit(avgCredit)
            .averageMonthlyDebit(avgDebit)
            .averageMonthlyBalance(avgBalance)
            .limitAmount(limitAmount)
            .monthlyLimit(monthlyLimit)
            .weeklyLimit(weeklyLimit)
            .expenditureBehavior(expenditureBehavior)
            .isExcluded(isExcluded)
            .exclusionReason(exclusionReason)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    scoreRepository.save(scoreEntity);

    // Build DTO to return
    ScoreDTO score = new ScoreDTO();
    score.setCustomerNumber(customerNumber);
    score.setAverageMonthlyCredit(avgCredit);
    score.setAverageMonthlyDebit(avgDebit);
    score.setAverageMonthlyBalance(avgBalance);
    score.setLimitAmount(limitAmount);
    score.setMonthlyLimit(monthlyLimit);
    score.setWeeklyLimit(weeklyLimit);
    score.setExpenditureBehavior(expenditureBehavior);
    score.setExcluded(isExcluded);
    score.setExclusionReason(exclusionReason);

    return score;
}


private double calculateLimit(double avgCredit, double avgBalance, double avgDebit) {
    // Basic formula: (60% of avgCredit + 20% of avgBalance) - 30% of avgDebit
    double limit = (0.6 * avgCredit + 0.2 * avgBalance) - (0.3 * avgDebit);
    return Math.max(limit, 0); // Ensure no negative limits
}


}
