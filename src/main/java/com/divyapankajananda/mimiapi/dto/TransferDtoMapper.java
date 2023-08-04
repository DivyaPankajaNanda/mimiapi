package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.entity.Transfer;
import com.divyapankajananda.mimiapi.entity.User;

@Component
public class TransferDtoMapper {
    @Autowired
    private AuditorAware<UUID> auditor;

    public Transfer toTransfer(TransferRequestDto transferRequestDto){
        return Transfer.builder()
            .goal(Goal.builder().goalId(transferRequestDto.getGoalId()).build())    
            .budget(Budget.builder().budgetId(transferRequestDto.getBudgetId()).build())
            .amount(transferRequestDto.getAmount())
            .user(User.builder().userId(auditor.getCurrentAuditor().get()).build())
            .build();
    }
}
