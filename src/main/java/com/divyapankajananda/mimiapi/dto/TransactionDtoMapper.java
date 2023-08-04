package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.entity.Transaction;
import com.divyapankajananda.mimiapi.entity.User;

@Component
public class TransactionDtoMapper {
    @Autowired
    private AuditorAware<UUID> auditor;

    public Transaction toTransaction(TransactionRequestDto transactionRequestDto){
        return Transaction.builder()
            .title(transactionRequestDto.getTitle())
            .description(transactionRequestDto.getDescription())
            .amount(transactionRequestDto.getAmount())
            .category(Category.builder().categoryId(transactionRequestDto.getCategoryId()).build())
            .budget(Budget.builder().budgetId(transactionRequestDto.getBudgetId()).build())
            .type(transactionRequestDto.getType())
            .happinessQuotient(transactionRequestDto.getHappinessQuotient())
            .necessityQuotient(transactionRequestDto.getNecessityQuotient())
            .assetUrl(transactionRequestDto.getAssetUrl())
            .user(User.builder().userId(auditor.getCurrentAuditor().get()).build())
            .build();

    }

    public Transaction toTransaction(Transaction existingTransaction, TransactionUpdateRequestDto transactionUpdateRequestDto){
        return Transaction.builder()
            .title(transactionUpdateRequestDto.getTitle())
            .description(transactionUpdateRequestDto.getDescription())
            .amount(transactionUpdateRequestDto.getAmount())
            .category(Category.builder().categoryId(transactionUpdateRequestDto.getCategoryId()).build())
            .budget(existingTransaction.getBudget())
            .type(existingTransaction.getType())
            .happinessQuotient(transactionUpdateRequestDto.getHappinessQuotient())
            .necessityQuotient(transactionUpdateRequestDto.getNecessityQuotient())
            .assetUrl(transactionUpdateRequestDto.getAssetUrl())
            .user(existingTransaction.getUser())
            .build();

    }
}
