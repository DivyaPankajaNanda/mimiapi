package com.divyapankajananda.mimiapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.BudgetDtoMapper;
import com.divyapankajananda.mimiapi.dto.BudgetRequestDto;
import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.repository.BudgetRepository;

@Service
public class BudgetService {
    
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetDtoMapper budgetDtoMapper;

    public Budget saveBudget(BudgetRequestDto budgetRequestDto){
        Budget budget = budgetDtoMapper.toBudget(budgetRequestDto);
        return budgetRepository.save(budget);
    }

    public Optional<List<Budget>> findAllOverlappingUserBudgets(LocalDate startDate, LocalDate endDate, UUID currentUserId){
        return budgetRepository.findAllOverlappingUserBudgets(startDate, endDate, currentUserId);
    }

    public Optional<List<Budget>> findAllUserBudgetsBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate, UUID currentUserId){
        return budgetRepository.findAllUserBudgetsBetweenStartAndEndDate(startDate, endDate, currentUserId);
    }

    public Optional<Budget> findBudget(UUID budgetid){
        return budgetRepository.findById(budgetid);
    }

    public Budget updateBudget(UUID budgetid, BudgetRequestDto budgetRequestDto){
        Budget budget = budgetDtoMapper.toBudget(budgetRequestDto);
        budget.setId(budgetid);

        return budgetRepository.save(budget);
    }

    public void deleteBudget(UUID budgetid){
        budgetRepository.deleteById(budgetid);
    }

}
