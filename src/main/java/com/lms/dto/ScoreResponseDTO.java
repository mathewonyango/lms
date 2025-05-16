package com.lms.dto;

import lombok.Data;

@Data
public class ScoreResponseDTO {
    private String customerNumber;
    private Double score;
    private Double creditLimit;
}
