package com.divyapankajananda.mimiapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.RecurringPayment;

public interface RecurringPaymentRepository extends JpaRepository<RecurringPayment, UUID> {
    
}