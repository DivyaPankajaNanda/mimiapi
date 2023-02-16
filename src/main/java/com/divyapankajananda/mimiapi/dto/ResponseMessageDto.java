package com.divyapankajananda.mimiapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseMessageDto {
    @NotBlank(message="Invalid message")
    private String message;
}
