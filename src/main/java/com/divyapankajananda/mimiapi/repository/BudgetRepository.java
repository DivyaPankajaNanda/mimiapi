package com.divyapankajananda.mimiapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.divyapankajananda.mimiapi.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget,UUID>{

    @Query(value = "select * from budget b where b.userid = ?3 and (b.start_date between ?1 and ?2 or b.end_date between ?1 and ?2)", nativeQuery = true)
    public Optional<List<Budget>> findAllOverlappingUserBudgets(LocalDate startDate, LocalDate endDate, UUID currentUserId);
    
    @Query(value = "select * from Budget b where b.userid=?3 and b.start_date between ?1 and ?2 or b.end_date between ?1 and ?2", nativeQuery = true)
    public Optional<List<Budget>> findAllUserBudgetsBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate, UUID currentUserId);

    @Query(value = "select * from Budget b where b.userid=?3 and b.start_date >= ?1 and b.end_date <= ?2", nativeQuery = true)
    public Optional<List<Budget>> findAllUserBudgetsStrictlyBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate, UUID currentUserId);

    @Query(value = "select * from Budget b where b.userid=?1", nativeQuery = true)
    public Optional<List<Budget>> findAllUserBudgets(UUID currentUserId);

    public Optional<Budget> findById(UUID id);

    @Modifying
    @Transactional
    public void deleteById(UUID id);
}
