package com.lms.repository;

import com.lms.model.ScoreEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<ScoreEntity, Long> {
        Optional<ScoreEntity> findTopByCustomerNumberOrderByCreatedAtDesc(String customerNumber);
        Optional<ScoreEntity> findByCustomerNumber(String customerNumber);
}
