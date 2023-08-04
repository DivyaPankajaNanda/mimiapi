package com.divyapankajananda.mimiapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.BudgetDtoMapper;
import com.divyapankajananda.mimiapi.dto.BudgetRequestDto;
import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.exception.ForbiddenActionException;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.BudgetRepository;

@Service
public class BudgetService {
    
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetDtoMapper budgetDtoMapper;

    public Budget saveUserBudget(BudgetRequestDto budgetRequestDto){

        Budget budget = budgetDtoMapper.toBudget(budgetRequestDto);

        LocalDate startDate = budget.getStartDate();
        LocalDate endDate = budget.getEndDate();
        UUID currentUserId = budget.getUser().getUserId();

        List<Budget> overlappingBudgets = findAllOverlappingUserBudgets(startDate, endDate, currentUserId);

        if (!overlappingBudgets.isEmpty()) {
            throw new ForbiddenActionException(String.format("Overlapping budgets present between %s and %s", startDate, endDate));
        }

        return budgetRepository.save(budget);
    }

    public List<Budget> findAllOverlappingUserBudgets(LocalDate startDate, LocalDate endDate, UUID userId){
        Optional<List<Budget>> budgetListOptional = budgetRepository.findAllOverlappingUserBudgets(startDate, endDate, userId);
        
        if(!budgetListOptional.isPresent())
        throw new ResourceNotFoundException("Budgets not found.");

        return budgetListOptional.get();
    }

    public List<Budget> findAllUserBudgetsBetweenStartAndEndDate(LocalDate startDate, LocalDate endDate, UUID currentUserId){
        Optional<List<Budget>> budgetListOptional = budgetRepository.findAllUserBudgetsBetweenStartAndEndDate(startDate, endDate, currentUserId);
        
        if(!budgetListOptional.isPresent() || budgetListOptional.get().isEmpty())
        throw new ResourceNotFoundException("Budgets not found.");

        return budgetListOptional.get();
    }

    public Budget findUserBudget(UUID userId, UUID budgetId) {
        Optional<Budget> budgetOptional = budgetRepository.findByUserUserIdAndBudgetId(userId, budgetId);
        
        if(!budgetOptional.isPresent())
        throw new ResourceNotFoundException("Budget not found.");

        return budgetOptional.get();
    }

    public Budget updateUserBudget(UUID budgetId, BudgetRequestDto budgetRequestDto) {
        Budget budget = budgetDtoMapper.toBudget(budgetRequestDto);
        budget.setBudgetId(budgetId);
        
        UUID userId = budget.getUser().getUserId();

        Optional<Budget> budgetOptional = budgetRepository.findByUserUserIdAndBudgetId(userId, budgetId);
        if(!budgetOptional.isPresent())
        throw new ResourceNotFoundException("Budget not found.");
        Budget existingBudget = budgetOptional.get();

        List<Budget> overlappingBudgets = findAllOverlappingUserBudgets(budget.getStartDate(), budget.getEndDate(), userId);
        overlappingBudgets.remove(existingBudget);
        if (!overlappingBudgets.isEmpty()) {
            throw new ForbiddenActionException(String.format("Overlapping budgets present between %s and %s", budget.getStartDate(), budget.getEndDate()));
        }

        Boolean existingBudgetHasTransactionsOrTransfers = !existingBudget.getTransactions().isEmpty() || !existingBudget.getTransfers().isEmpty();
        Boolean newBudgetDateRangeIsReduced = existingBudget.getStartDate().isBefore(budget.getStartDate()) || existingBudget.getEndDate().isAfter(budget.getStartDate());
        if(newBudgetDateRangeIsReduced && existingBudgetHasTransactionsOrTransfers)
        throw new ForbiddenActionException("Budget range can't be reduced since it has transaction and/or transfers mapped to it.");

        return budgetRepository.save(budget);
    }

    public Budget updateUserBudget(Budget budget) {       
        UUID budgetId = budget.getBudgetId();
        UUID userId = budget.getUser().getUserId();

        Optional<Budget> budgetOptional = budgetRepository.findByUserUserIdAndBudgetId(userId,budgetId);
        if(!budgetOptional.isPresent())
        throw new ResourceNotFoundException("Budget not found.");
        Budget existingBudget = budgetOptional.get();

        List<Budget> overlappingBudgets = findAllOverlappingUserBudgets(budget.getStartDate(), budget.getEndDate(), userId);
        overlappingBudgets.remove(existingBudget);
        if (!overlappingBudgets.isEmpty()) {
            throw new ForbiddenActionException(String.format("Overlapping budgets present between %s and %s", budget.getStartDate(), budget.getEndDate()));
        }

        Boolean existingBudgetHasTransactionsOrTransfers = !existingBudget.getTransactions().isEmpty() || !existingBudget.getTransfers().isEmpty();
        Boolean newBudgetDateRangeIsReduced = existingBudget.getStartDate().isBefore(budget.getStartDate()) || existingBudget.getEndDate().isAfter(budget.getStartDate());
        if(newBudgetDateRangeIsReduced && existingBudgetHasTransactionsOrTransfers)
        throw new ForbiddenActionException("Budget range can't be reduced since it has transaction and/or transfers mapped to it.");

        return budgetRepository.save(budget);
    }

    public void deleteUserBudget(UUID userId, UUID budgetId) {
        Optional<Budget> budgetOptional = budgetRepository.findByUserUserIdAndBudgetId(userId, budgetId);
        
        if(!budgetOptional.isPresent())
        throw new ResourceNotFoundException("Budget not found.");

        Budget existingBudget = budgetOptional.get();
        Boolean existingBudgetHasTransactionsOrTransfers = !existingBudget.getTransactions().isEmpty() || !existingBudget.getTransfers().isEmpty();
        if(existingBudgetHasTransactionsOrTransfers)
        throw new ForbiddenActionException("Budget can't be deleted since it has transaction and/or transfers mapped to it.");

        budgetRepository.delete(existingBudget);
        return;
    }

}
