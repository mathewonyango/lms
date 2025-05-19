package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}

