package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.model.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
}
