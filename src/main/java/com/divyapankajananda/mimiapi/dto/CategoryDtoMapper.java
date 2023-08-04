package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.entity.Icon;
import com.divyapankajananda.mimiapi.entity.User;

@Component
public class CategoryDtoMapper {
    @Autowired
    private AuditorAware<UUID> auditor;
    
    public Category toCategory(CategoryRequestDto categoryRequestDto){    
        return Category.builder()
            .title(categoryRequestDto.getTitle())
            .icon(Icon.builder().iconId(categoryRequestDto.getIconId()).build())
            .user(User.builder().userId(auditor.getCurrentAuditor().get()).build())
            .build();
    }
}
