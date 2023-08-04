package com.divyapankajananda.mimiapi.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
public class BudgetRequestDto {
    
    @DecimalMin(value = "0.0", message = "Invalid total budget")
    @NotNull
    private Double totalBudget;

    @DecimalMin(value = "0.0", message = "Invalid goal budget")
    @NotNull
    private Double goalBudget;

    @DecimalMin(value = "0.0", message = "Invalid expense budget")
    @NotNull
    private Double expenseBudget;

    @DecimalMin(value = "0.0", message = "Invalid expense amount")
    @NotNull
    private Double expenseAmount;

    @DecimalMin(value = "0.0", message = "Invalid goal amount")
    @NotNull
    private Double goalAmount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = Constants.DATE_PATTERN) 
    @JsonFormat(pattern = Constants.DATE_PATTERN)
    @NotNull
    private LocalDate startDate;
        
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = Constants.DATE_PATTERN) 
    @JsonFormat(pattern = Constants.DATE_PATTERN) 
    @NotNull
    private LocalDate endDate;

}
