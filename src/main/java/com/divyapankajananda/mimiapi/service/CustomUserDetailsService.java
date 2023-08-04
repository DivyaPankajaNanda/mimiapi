package com.divyapankajananda.mimiapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.UserDtoMapper;
import com.divyapankajananda.mimiapi.dto.UserResponseDto;
import com.divyapankajananda.mimiapi.entity.CurrencyType;
import com.divyapankajananda.mimiapi.entity.User;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> user = userRepository.findByUsername(username);
        return user.get();
    }

    public Boolean userExists(String username){
        return userRepository.existsByUsername(username);
    }

    public UserResponseDto updateUserCurrency(UUID currentUserId, CurrencyType currency) {
        Optional<User> userOptional = userRepository.findByUserId(currentUserId);
        if(!userOptional.isPresent())
        throw new ResourceNotFoundException("User not found.");

        User user = userOptional.get();
        user.setCurrency(currency);
        user = userRepository.save(user);
        UserResponseDto userResponseDto = userDtoMapper.toUserResponseDto(user);
        return userResponseDto;
    }

}
