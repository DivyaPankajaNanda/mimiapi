package com.divyapankajananda.mimiapi.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.divyapankajananda.mimiapi.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecurringTransferRequestDto {
    @NotNull
    private UUID goalId;

    @NotNull
    private Double amount;

    @NotNull
    private Integer intervalDays;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = Constants.DATE_PATTERN) 
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @NotNull
    private LocalDate triggerDate;
    
    @NotNull
    private boolean isAutomated;
    

}
