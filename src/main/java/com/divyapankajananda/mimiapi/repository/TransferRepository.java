package com.divyapankajananda.mimiapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.Transfer;

public interface TransferRepository extends JpaRepository<Transfer,UUID>{
    public Page<Transfer> findAllByUserUserIdAndGoalGoalId(UUID userId, UUID goalId, Pageable pageable);

    public Page<Transfer> findAllByUserUserIdAndBudgetBudgetId(UUID userId, UUID budgetId, Pageable pageable);

    public Optional<Transfer> findByUserUserIdAndTransferId(UUID userId, UUID transferId);
}
