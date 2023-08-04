package com.divyapankajananda.mimiapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.GoalDtoMapper;
import com.divyapankajananda.mimiapi.dto.GoalRequestDto;
import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.exception.ForbiddenActionException;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.GoalRepository;

@Service
public class GoalService {
    
    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoalDtoMapper goalDtoMapper;

    public Goal saveUserGoal(GoalRequestDto goalRequestDto){
        Goal goal = goalDtoMapper.toGoal(goalRequestDto);
        Category category = categoryService.findCategoryByUserIdAndCategoryId(goal.getUser().getUserId(), goal.getCategory().getCategoryId());
        goal.setCategory(category);
        return goalRepository.save(goal);
    }

    public Goal saveUserGoal(Goal goal){
        Category category = categoryService.findCategoryByUserIdAndCategoryId(goal.getUser().getUserId(), goal.getCategory().getCategoryId());
        goal.setCategory(category);
        return goalRepository.save(goal);
    }

    public Page<Goal> findAllUserGoalsWithPagination(UUID userId, int offset, int size){
        return goalRepository.findAllByUserUserId(userId, PageRequest.of(offset, size));
    }

    public Goal findGoalByUserIdAndGoalId(UUID userId, UUID goalId){
        Optional<Goal> goalOptional = goalRepository.findByUserUserIdAndGoalId(userId, goalId);

        if(!goalOptional.isPresent())
        throw new ResourceNotFoundException("Goal not found.");

        return goalOptional.get();
    }

    public Goal updateUserGoal(UUID userId, UUID goalId, GoalRequestDto goalRequestDto){
        Optional<Goal> goalOptional = goalRepository.findByUserUserIdAndGoalId(userId, goalId);
        if(!goalOptional.isPresent())
        throw new ResourceNotFoundException("Goal not found.");
        
        Goal existingGoal = goalOptional.get();
        if(existingGoal.isClaimed())
        throw new ForbiddenActionException("Goal is already claimed and can't be updated.");

        Goal goal = goalDtoMapper.toGoal(goalRequestDto);
        goal.setGoalId(goalId);
        goalRepository.save(goal);
        return goal;
    }

    public void deleteUserGoal(UUID userId, UUID goalId){
        Optional<Goal> goalOptional = goalRepository.findByUserUserIdAndGoalId(userId, goalId);
        if(!goalOptional.isPresent())
        throw new ResourceNotFoundException("Goal not found.");

        if(!goalOptional.get().getTransfers().isEmpty())
        throw new ForbiddenActionException("Goal can' be deleted since it has transfers mapped to it.");

        goalRepository.delete(goalOptional.get());
        return;
    }

    public Boolean goalByCategoryIdExists(UUID categoryId) {
        Long count = goalRepository.findAllByCategoryCategoryIdWithLimit(categoryId, 1).count();
        return count!=0;
    }

}
