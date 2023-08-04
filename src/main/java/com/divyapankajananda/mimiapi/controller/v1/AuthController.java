package com.divyapankajananda.mimiapi.controller.v1;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divyapankajananda.mimiapi.dto.CustomExceptionDto;
import com.divyapankajananda.mimiapi.dto.JwtTokenDto;
import com.divyapankajananda.mimiapi.dto.ResponseMessageDto;
import com.divyapankajananda.mimiapi.dto.SigninRequestDto;
import com.divyapankajananda.mimiapi.dto.SignupRequestDto;
import com.divyapankajananda.mimiapi.dto.UserResponseDto;
import com.divyapankajananda.mimiapi.exception.ForbiddenActionException;
import com.divyapankajananda.mimiapi.service.AuthService;
import com.divyapankajananda.mimiapi.service.CustomUserDetailsService;
import com.divyapankajananda.mimiapi.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_V1_PREFIX+"auth")
@Tag(name = "Auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/signin")
    @Operation(summary = "Signin.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtTokenDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<JwtTokenDto> signin(@RequestBody @Valid SigninRequestDto signinRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(authService.signin(signinRequestDto));
    }

    @PostMapping("/signup")
    @Operation(summary = "Signup.")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessageDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) throws ForbiddenActionException {
        if(userDetailsService.userExists(signupRequestDto.getUsername())){
            throw new ForbiddenActionException(String.format("Username %s already exists",signupRequestDto.getUsername()));
        }else{
            authService.signup(signupRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessageDto("Signup successful"));
        }
    }
   
    @PostMapping("/me")
    @Operation(summary = "User details.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> userProfile() {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        UserResponseDto userResponseDto = authService.userProfile(currentUserId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(userResponseDto);
    }

}
