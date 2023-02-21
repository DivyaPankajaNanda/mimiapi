package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

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
public class PaymentRequestDto {
    
    @NotBlank(message = "Invalid Payment title")
    private String title;
    
    private String description;
    
    @DecimalMin(value = "0.0", message = "Invalid amount")
    @NotNull
    private Double amount;
    
    private UUID categoryId;
    
    private String type;
        
    private int happinessQuotient;

    private int necessityQuotient;    

}
