package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.model.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
