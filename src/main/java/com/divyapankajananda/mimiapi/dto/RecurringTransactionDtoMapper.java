package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.entity.RecurringTransaction;
import com.divyapankajananda.mimiapi.entity.User;

@Component
public class RecurringTransactionDtoMapper {
    @Autowired
    private AuditorAware<UUID> auditor;

    public RecurringTransaction toRecurringTransaction(RecurringTransactionRequestDto recurringTransactionRequestDto){
        return RecurringTransaction.builder()
            .title(recurringTransactionRequestDto.getTitle())
            .description(recurringTransactionRequestDto.getDescription())
            .amount(recurringTransactionRequestDto.getAmount())
            .category(Category.builder().categoryId(recurringTransactionRequestDto.getCategoryId()).build())
            .type(recurringTransactionRequestDto.getType())
            .assetUrl(recurringTransactionRequestDto.getAssetUrl())
            .user(User.builder().userId(auditor.getCurrentAuditor().get()).build())
            .intervalDays(recurringTransactionRequestDto.getIntervalDays())
            .triggerDate(recurringTransactionRequestDto.getTriggerDate())
            .isAutomated(recurringTransactionRequestDto.isAutomated())
            .build();

    }
}
