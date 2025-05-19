package com.lms.dto;

import lombok.Data;

@Data
public class ScoreDetailsResponse {
    private int score;
    private String exclusionReason;
    private int limitAmount;
    private String exclusion;
    private Long id;
    private String customerNumber;
}





// ScoreEntity entity = new ScoreEntity();
//         entity.setScore(response.getScore());
//         entity.setExclusionReason(response.getExclusionReason());
//         entity.setLimitAmount((double) response.getLimitAmount());
//         entity.setExclusionReason(response.getExclusion());
//         entity.setRemoteId(response.getId());
//         entity.setCustomerNumber(response.getCustomerNumber());
