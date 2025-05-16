package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.model.ScoringClient;

import java.util.Optional;

public interface ScoringClientRepository extends JpaRepository<ScoringClient, Long> {
    
    Optional<ScoringClient> findByActiveTrue();
    
    Optional<ScoringClient> findByToken(String token);
    Optional<ScoringClient> findByClientId(String clientId);
    Optional<ScoringClient> findByUrl(String url);
    Optional<ScoringClient> findByName(String name);
    Optional<ScoringClient> findByUsername(String username);
    // findAllById

}