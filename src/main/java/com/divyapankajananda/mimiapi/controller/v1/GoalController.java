package com.divyapankajananda.mimiapi.controller.v1;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divyapankajananda.mimiapi.dto.GoalRequestDto;
import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.service.GoalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("mimiapi/v1/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    public ResponseEntity<Object> saveGoal(@RequestBody @Valid GoalRequestDto goalRequestDto) {
        Goal goal = goalService.saveGoal(goalRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(goal);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAllUserGoals(@RequestParam int offset,@RequestParam int size) {
        UUID currentUserId = auditor.getCurrentAuditor().get();

        Page<Goal> goals = goalService.findAllUserGoalsWithPagination(currentUserId,offset,size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(goals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGoal(@PathVariable UUID id, @RequestBody @Valid GoalRequestDto goalRequestDto) throws ResourceNotFoundException {
        Optional<Goal> existing_goal = goalService.findGoalById(id);

        if(existing_goal.isPresent()){
            Goal goal = goalService.updateGoal(id,goalRequestDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(goal);
        }else
            throw new ResourceNotFoundException(String.format("Goal with %s doesn't exist.",id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGoal(@PathVariable UUID id) throws ResourceNotFoundException {
        Optional<Goal> existing_goal = goalService.findGoalById(id);

        if(existing_goal.isPresent()){
            goalService.deleteGoal(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }else
            throw new ResourceNotFoundException(String.format("Goal with %s doesn't exist.",id));

    }
}
