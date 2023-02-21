package com.divyapankajananda.mimiapi.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Page<Payment> findAllByUserId(UUID userId, Pageable pageable);
}