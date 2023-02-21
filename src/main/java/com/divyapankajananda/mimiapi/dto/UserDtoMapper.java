package com.divyapankajananda.mimiapi.dto;

import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.User;

@Component
public class UserDtoMapper {
    public UserResponseDto toUserResponseDto(User user){
        return UserResponseDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .name(user.getName())
                    .role(user.getRole())
                    .createdAt(user.getCreatedAt())
                    .build();
    }
}
