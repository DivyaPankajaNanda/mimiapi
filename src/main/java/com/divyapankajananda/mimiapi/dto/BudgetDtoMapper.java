package com.divyapankajananda.mimiapi.dto;

import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Budget;

@Component
public class BudgetDtoMapper {
    public Budget toBudget(BudgetRequestDto budgetRequestDto){
        return Budget.builder()
            .totalBudget(budgetRequestDto.getTotalBudget())
            .savingBudget(budgetRequestDto.getSavingBudget())
            .expenseBudget(budgetRequestDto.getExpenseBudget())
            .investmentBudget(budgetRequestDto.getInvestmentBudget())
            .savingAmount(budgetRequestDto.getSavingAmount())
            .expenseAmount(budgetRequestDto.getExpenseAmount())
            .investmentAmount(budgetRequestDto.getInvestmentAmount())
            .currency(budgetRequestDto.getCurrency())
            .startDate(budgetRequestDto.getStartDate())
            .endDate(budgetRequestDto.getEndDate())
            .build();
    }
}
