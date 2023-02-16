package com.divyapankajananda.mimiapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SigninRequestDto {

    @Email(message = "Invalid username")
    @NotBlank
    private String username;

    @NotBlank(message = "Invalid password")
    private String password;
}