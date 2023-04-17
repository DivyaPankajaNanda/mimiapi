package com.divyapankajananda.mimiapi.dto;

import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Transaction;

@Component
public class TransactionDtoMapper {
    public Transaction toTransaction(TransactionRequestDto transactionRequestDto){
        return Transaction.builder()
            .title(transactionRequestDto.getTitle())
            .description(transactionRequestDto.getDescription())
            .amount(transactionRequestDto.getAmount())
            .type(transactionRequestDto.getType())
            .categoryId(transactionRequestDto.getCategoryId())
            .happinessQuotient(transactionRequestDto.getHappinessQuotient())
            .necessityQuotient(transactionRequestDto.getNecessityQuotient())
            .assetUrl(null)
            .build();

    }
}
