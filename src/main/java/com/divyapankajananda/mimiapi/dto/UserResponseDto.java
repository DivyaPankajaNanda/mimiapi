package com.divyapankajananda.mimiapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    
    private UUID id;

    private String name;

    private String username;

    private String role;

    private LocalDateTime createdAt;

}
