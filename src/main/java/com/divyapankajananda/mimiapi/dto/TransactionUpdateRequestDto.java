package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class TransactionUpdateRequestDto {
    
    @NotBlank(message = "Invalid Payment title")
    private String title;
    
    private String description;

    private String assetUrl;
    
    @DecimalMin(value = "0.0", message = "Invalid amount")
    @NotNull
    private Double amount;
    
    @NotNull
    private UUID categoryId;

    @Min(value = 0, message = "Happiness quotient range must be between 0 to 10")
    @Max(value = 10, message = "Happiness quotient range must be between 0 to 10")
    private Integer happinessQuotient;

    @Min(value = 0, message = "Necessity quotient range must be between 0 to 10")
    @Max(value = 10, message = "Necessity quotient range must be between 0 to 10")
    private Integer necessityQuotient; 

}
