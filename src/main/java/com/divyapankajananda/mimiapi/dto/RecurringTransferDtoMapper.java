package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.entity.RecurringTransfer;
import com.divyapankajananda.mimiapi.entity.User;

@Component
public class RecurringTransferDtoMapper {
    @Autowired
    private AuditorAware<UUID> auditor;

    public RecurringTransfer toRecurringTransfer(RecurringTransferRequestDto recurringTransferRequestDto){
        return RecurringTransfer.builder()
            .goal(Goal.builder().goalId(recurringTransferRequestDto.getGoalId()).build())    
            .amount(recurringTransferRequestDto.getAmount())
            .intervalDays(recurringTransferRequestDto.getIntervalDays())
            .triggerDate(recurringTransferRequestDto.getTriggerDate())
            .isAutomated(recurringTransferRequestDto.isAutomated())
            .user(User.builder().userId(auditor.getCurrentAuditor().get()).build())
            .build();
    }
}
