package com.lms.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.model.Subscription;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    Optional<Subscription> findByCustomerNumber(String customerNumber);
    
    boolean existsByCustomerNumberAndStatus(String customerNumber, Subscription.SubscriptionStatus status);
}