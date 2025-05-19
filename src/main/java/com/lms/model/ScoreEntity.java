package com.lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerNumber;

    private Double averageMonthlyCredit;
    private Double averageMonthlyDebit;
    private Double averageMonthlyBalance;

    private Double limitAmount;
    private Double monthlyLimit;
    private Double weeklyLimit;

    private Boolean isExcluded;
    private String exclusionReason;
    private String expenditureBehavior;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer score;
    // setRemoteId
    private String remoteId;
}
