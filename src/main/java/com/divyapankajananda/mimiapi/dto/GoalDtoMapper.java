package com.divyapankajananda.mimiapi.dto;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.entity.User;

@Component
public class GoalDtoMapper {
    @Autowired
    private AuditorAware<UUID> auditor;
    
    public Goal toGoal(GoalRequestDto goalRequestDto){    
        return Goal.builder()
            .title(goalRequestDto.getTitle())
            .description(goalRequestDto.getDescription())
            .amountRequired(goalRequestDto.getAmountRequired())
            .amountSaved(goalRequestDto.getAmountSaved())
            .category(Category.builder().categoryId(goalRequestDto.getCategoryId()).build())
            .type(goalRequestDto.getType())
            .startDate(goalRequestDto.getStartDate())
            .endDate(goalRequestDto.getEndDate())
            .isClaimed(goalRequestDto.isClaimed())
            .user(User.builder().userId(auditor.getCurrentAuditor().get()).build())
            .build();
    }
}
