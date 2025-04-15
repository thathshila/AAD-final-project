package org.example.netwave.repo;

import org.example.netwave.entity.OTPVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepo extends JpaRepository<OTPVerification, Long> {
    Optional<OTPVerification> findTopByEmailOrderByExpiryTimeDesc(String email);
}

