package com.divyapankajananda.mimiapi.dto;

import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Payment;

@Component
public class PaymentDtoMapper {
    public Payment toPayment(PaymentRequestDto paymentRequestDto){
        return Payment.builder()
            .title(paymentRequestDto.getTitle())
            .description(paymentRequestDto.getDescription())
            .amount(paymentRequestDto.getAmount())
            .type(paymentRequestDto.getType())
            .categoryId(paymentRequestDto.getCategoryId())
            .happinessQuotient(paymentRequestDto.getHappinessQuotient())
            .necessityQuotient(paymentRequestDto.getNecessityQuotient())
            .assetUrl(null)
            .build();

    }
}
