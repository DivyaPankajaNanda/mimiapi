package com.divyapankajananda.mimiapi.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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

    @DecimalMin(value = "0.0", message = "Invalid investment budget")
    @NotNull
    private Double invstmentBudget;

    @DecimalMin(value = "0.0", message = "Invalid expense budget")
    @NotNull
    private Double expenseBudget;

    @DecimalMin(value = "0.0", message = "Invalid saving budget")
    @NotNull
    private Double savingBudget;

    @NotBlank(message = "Invalid currency")
    private String currency;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") 
    @JsonFormat(pattern = "yyyy-MM-dd") 
    private LocalDate startDate;
        
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") 
    @JsonFormat(pattern = "yyyy-MM-dd") 
    private LocalDate endDate;

}
