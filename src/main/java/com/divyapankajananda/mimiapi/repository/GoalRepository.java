package com.divyapankajananda.mimiapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.divyapankajananda.mimiapi.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, UUID> {
    public Page<Goal> findAllByUserUserId(UUID userId, Pageable pageable);

    public Optional<Goal> findByUserUserIdAndGoalId(UUID userId, UUID goalId);

    public CrudRepository<Goal, UUID> findAllByCategoryCategoryIdWithLimit(UUID categoryId, int i);
}