package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.entity.User;

@Component
public class BudgetDtoMapper {
    @Autowired  
    private AuditorAware<UUID> auditor;
    
    public Budget toBudget(BudgetRequestDto budgetRequestDto){
        return Budget.builder()
            .totalBudget(budgetRequestDto.getTotalBudget())
            .expenseBudget(budgetRequestDto.getExpenseBudget())
            .expenseAmount(budgetRequestDto.getExpenseAmount())
            .goalBudget(budgetRequestDto.getGoalBudget())
            .goalAmount(budgetRequestDto.getGoalAmount())
            .startDate(budgetRequestDto.getStartDate())
            .endDate(budgetRequestDto.getEndDate())
            .user(User.builder().userId(auditor.getCurrentAuditor().get()).build())
            .build();
    }
}
