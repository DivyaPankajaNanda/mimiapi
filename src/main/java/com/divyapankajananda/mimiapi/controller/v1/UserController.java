package com.divyapankajananda.mimiapi.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divyapankajananda.mimiapi.dto.CustomExceptionDto;
import com.divyapankajananda.mimiapi.dto.UserCurrencyUpdateRequestDto;
import com.divyapankajananda.mimiapi.dto.UserResponseDto;
import com.divyapankajananda.mimiapi.service.CustomUserDetailsService;
import com.divyapankajananda.mimiapi.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_V1_PREFIX+"user")
@Tag(name = "User")
public class UserController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PutMapping("/currency")
    @Operation(summary = "Update user currency.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> updateUserCurrency(@RequestBody @Valid UserCurrencyUpdateRequestDto userCurrencyUpdateRequestDto){
        UUID currentUserId = auditor.getCurrentAuditor().get();
        UserResponseDto userResponseDto = customUserDetailsService.updateUserCurrency(currentUserId, userCurrencyUpdateRequestDto.getCurrency());
        return ResponseEntity.status(HttpStatus.OK)
                .body(userResponseDto);
    }


}
