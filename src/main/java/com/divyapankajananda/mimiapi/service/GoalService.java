package com.divyapankajananda.mimiapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.GoalDtoMapper;
import com.divyapankajananda.mimiapi.dto.GoalRequestDto;
import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.repository.GoalRepository;

@Service
public class GoalService {
    
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalDtoMapper goalDtoMapper;

    public Goal saveGoal(GoalRequestDto goalRequestDto){
        Goal goal = goalDtoMapper.toGoal(goalRequestDto);
        return goalRepository.save(goal);
    }

    public Page<Goal> findAllUserGoalsWithPagination(UUID userId, int offset, int size){
        return goalRepository.findAllByUserId(userId, PageRequest.of(offset, size));
    }

    public Optional<Goal> findGoalById(UUID goalId){
        return goalRepository.findById(goalId);
    }

    public Goal updateGoal(UUID goalId, GoalRequestDto goalRequestDto){
        Goal goal = goalDtoMapper.toGoal(goalRequestDto);
        goal.setId(goalId);
        goalRepository.save(goal);
        return goal;
    }

    public void deleteGoal(UUID goalId){
        goalRepository.deleteById(goalId);
        return;
    }

}
