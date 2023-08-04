package com.divyapankajananda.mimiapi.controller.v1;

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

import com.divyapankajananda.mimiapi.dto.CustomExceptionDto;
import com.divyapankajananda.mimiapi.dto.GoalRequestDto;
import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.service.GoalService;
import com.divyapankajananda.mimiapi.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_V1_PREFIX+"goal")
@Tag(name = "Goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    @Operation(summary = "Save goal.")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> saveUserGoal(@RequestBody @Valid GoalRequestDto goalRequestDto) {
        Goal goal = goalService.saveUserGoal(goalRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(goal);
    }

    @GetMapping("/")
    @Operation(summary = "Get all user goals.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Goal.class))))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> findAllUserGoals(@RequestParam @Valid int offset,@RequestParam @Valid int size) {
        UUID currentUserId = auditor.getCurrentAuditor().get();

        Page<Goal> goals = goalService.findAllUserGoalsWithPagination(currentUserId,offset,size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(goals);
    }

    @PutMapping("/{goalid}")
    @Operation(summary = "Update goal.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> updateUserGoal(@PathVariable("goalid") @Valid UUID goalId, @RequestBody @Valid GoalRequestDto goalRequestDto) {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        Goal goal = goalService.updateUserGoal(currentUserId, goalId,goalRequestDto);
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(goal);
    }

    @DeleteMapping("/{goalid}")
    @Operation(summary = "Delete goal.")
    @ApiResponse(responseCode = "204", description = "Success,No content", content = @Content())
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> deleteUserGoal(@PathVariable("goalid") UUID goalId) {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        goalService.deleteUserGoal(currentUserId, goalId);
        
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
