package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestDto {
    @NotNull
    private UUID goalId;

    @NotNull
    private UUID budgetId;

    @NotNull
    private Double amount;

}
