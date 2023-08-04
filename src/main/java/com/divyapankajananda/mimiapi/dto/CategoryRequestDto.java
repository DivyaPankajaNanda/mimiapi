package com.divyapankajananda.mimiapi.dto;

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
public class CategoryRequestDto {
          
    @NotBlank(message="Invalid category title.")
    private String title;
    
    @NotNull(message="Select a valid icon.")
    private Long iconId;

}
