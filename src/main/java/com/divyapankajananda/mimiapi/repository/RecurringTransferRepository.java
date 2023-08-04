package com.divyapankajananda.mimiapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.RecurringTransfer;

public interface RecurringTransferRepository extends JpaRepository<RecurringTransfer, UUID>{
    public Page<RecurringTransfer> findAllByUserUserId(UUID userId, Pageable pageable);

    public Optional<RecurringTransfer> findByUserUserIdAndTransferId(UUID userId, UUID transferId);

    public Page<RecurringTransfer> findAllByUserUserIdAndGoalGoalId(UUID userId, UUID goalId, Pageable pageable);
}
