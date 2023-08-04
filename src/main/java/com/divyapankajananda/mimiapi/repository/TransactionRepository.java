package com.divyapankajananda.mimiapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    public Page<Transaction> findAllByUserUserIdAndBudgetBudgetId(UUID userId, UUID budgetId, Pageable pageable);

    public Optional<Transaction> findByUserUserIdAndTransactionId(UUID userId, UUID transactionId);

    public Page<Transaction> findAllByUserUserId(UUID userId, Pageable pageable);
}