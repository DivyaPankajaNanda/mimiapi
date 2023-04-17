package com.divyapankajananda.mimiapi.dto;

import org.springframework.stereotype.Component;
import com.divyapankajananda.mimiapi.entity.Goal;

@Component
public class GoalDtoMapper {
    public Goal toGoal(GoalRequestDto goalRequestDto){
        return Goal.builder()
            .title(goalRequestDto.getTitle())
            .description(goalRequestDto.getDescription())
            .amountRequired(goalRequestDto.getAmountRequired())
            .amountSaved(goalRequestDto.getAmountSaved())
            .categoryId(goalRequestDto.getCategoryId())
            .type(goalRequestDto.getType())
            .startDate(goalRequestDto.getStartDate())
            .endDate(goalRequestDto.getEndDate())
            .completed(goalRequestDto.isCompleted())
            .build();
    }
}
