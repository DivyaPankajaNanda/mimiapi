package com.divyapankajananda.mimiapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divyapankajananda.mimiapi.dto.JwtTokenDto;
import com.divyapankajananda.mimiapi.dto.ResponseMessageDto;
import com.divyapankajananda.mimiapi.dto.SigninRequestDto;
import com.divyapankajananda.mimiapi.dto.SignupRequestDto;
import com.divyapankajananda.mimiapi.exception.DuplicateUserException;
import com.divyapankajananda.mimiapi.service.AuthService;
import com.divyapankajananda.mimiapi.service.CustomUserDetailsService;

import jakarta.validation.Valid;


@RestController
@EnableMethodSecurity
@RequestMapping("mimiapi/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<JwtTokenDto> signin(@RequestBody @Valid SigninRequestDto signinRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(authService.signin(signinRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        if(userDetailsService.userExists(signupRequestDto.getUsername())){
            throw new DuplicateUserException(String.format("Username %s already exists",signupRequestDto.getUsername()));
            // return ResponseEntity.status(HttpStatus.FORBIDDEN)
            //     .body(new ResponseMessageDto(String.format("Username %s already exists",signupRequestDto.getUsername())));
        }else{
            authService.signup(signupRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseMessageDto("Signup successful"));
        }
    }

}
