package com.divyapankajananda.mimiapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {
    RUPEE("\u20B9"),
    DOLLAR("\u0024"),
    EURO("\u20AC");

    private final String unicode;
}
