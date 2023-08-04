package com.divyapankajananda.mimiapi.dto;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.divyapankajananda.mimiapi.entity.TransactionType;
import com.divyapankajananda.mimiapi.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalRequestDto {

    @NotNull(message = "Invalid goal title")
    private String title;
    
    private String description;
    
    @DecimalMin(value = "0.0", message = "Invalid goal amount")
    @NotNull
    private Double amountRequired;
    
    @DecimalMin(value = "0.0", message = "Invalid goal savings")
    @NotNull
    private Double amountSaved;
    
    @NotNull(message = "Choose a valid category")
    private UUID categoryId;
    
    @NotNull(message = "Choose a valid type")
    private TransactionType type;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = Constants.DATE_PATTERN) 
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @NotNull
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = Constants.DATE_PATTERN) 
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @NotNull
    private LocalDate endDate;

    private boolean isClaimed;

}
