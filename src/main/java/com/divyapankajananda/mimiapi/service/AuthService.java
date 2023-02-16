package com.divyapankajananda.mimiapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.JwtTokenDto;
import com.divyapankajananda.mimiapi.dto.SigninRequestDto;
import com.divyapankajananda.mimiapi.dto.SignupRequestDto;
import com.divyapankajananda.mimiapi.entity.User;
import com.divyapankajananda.mimiapi.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public JwtTokenDto signin(SigninRequestDto signinRequestDto) {

        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            return JwtTokenDto.builder()
                    .token(jwtService.generateToken(username))
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    public void signup(SignupRequestDto signupRequestDto) {

        String name = signupRequestDto.getName();
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        User user = User.builder()
                .name(name)
                .username(username)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .build();

        userRepository.save(user);

    }

}
