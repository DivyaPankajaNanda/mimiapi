package com.divyapankajananda.mimiapi.dto;

import com.divyapankajananda.mimiapi.entity.CurrencyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCurrencyUpdateRequestDto {
    private CurrencyType currency;
}
