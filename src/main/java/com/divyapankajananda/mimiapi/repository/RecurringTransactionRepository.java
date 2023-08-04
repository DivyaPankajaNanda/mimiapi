package com.divyapankajananda.mimiapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.RecurringTransaction;

public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, UUID> {
    public Page<RecurringTransaction> findAllByUserUserId(UUID userId, Pageable pageable);
    
    public Optional<RecurringTransaction> findByUserUserIdAndTransactionId(UUID userId, UUID transactionId);
}