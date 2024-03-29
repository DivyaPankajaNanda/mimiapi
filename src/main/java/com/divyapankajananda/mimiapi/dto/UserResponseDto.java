package com.divyapankajananda.mimiapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.divyapankajananda.mimiapi.entity.CurrencyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    
    private UUID userId;

    private String name;

    private String username;

    private String role;

    private CurrencyType currency;

    private LocalDateTime createdAt;

}
