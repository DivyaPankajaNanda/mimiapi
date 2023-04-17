package com.divyapankajananda.mimiapi.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findAllByUserId(UUID userId, Pageable pageable);
}