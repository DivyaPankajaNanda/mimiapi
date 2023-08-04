package com.divyapankajananda.mimiapi.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.divyapankajananda.mimiapi.entity.TransactionType;
import com.divyapankajananda.mimiapi.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecurringTransactionRequestDto {
    @NotBlank(message = "Invalid Payment title")
    private String title;
    
    private String description;

    private String assetUrl;
    
    @DecimalMin(value = "0.0", message = "Invalid amount")
    @NotNull
    private Double amount;
    
    @NotNull
    private UUID categoryId;

    @NotNull
    private TransactionType type;
        
    @NotNull
    private Integer intervalDays;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = Constants.DATE_PATTERN) 
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @NotNull
    private LocalDate triggerDate;
    
    @NotNull
    private boolean isAutomated;
    
}
