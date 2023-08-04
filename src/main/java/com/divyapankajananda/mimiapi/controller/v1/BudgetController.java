package com.divyapankajananda.mimiapi.controller.v1;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.divyapankajananda.mimiapi.dto.BudgetRequestDto;
import com.divyapankajananda.mimiapi.dto.CustomExceptionDto;
import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.service.BudgetService;
import com.divyapankajananda.mimiapi.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_V1_PREFIX+"budget")
@Tag(name = "Budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    @Operation(summary = "Save budget.")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Budget.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> saveUserBudget(@RequestBody @Valid BudgetRequestDto budgetRequestDto) {
        Budget budget = budgetService.saveUserBudget(budgetRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(budget);
    }

    @GetMapping("/")
    @Operation(summary = "Get all budgets between provided start date and end date.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Budget.class))))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> findAllUserBudgetsBetweenStartAndEndDate(@RequestParam @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        List<Budget> budgets = budgetService.findAllUserBudgetsBetweenStartAndEndDate(startDate, endDate, currentUserId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(budgets);
    }

    @PutMapping("/{budgetid}")
    @Operation(summary = "Update budget.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Budget.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> updateUserBudget(@PathVariable("budgetid") @Valid UUID budgetId, @RequestBody @Valid BudgetRequestDto budgetRequestDto) {
        Budget updatedBudget = budgetService.updateUserBudget(budgetId, budgetRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedBudget);
    }

    @DeleteMapping("/{budgetid}")
    @Operation(summary = "Delete budget.")
    @ApiResponse(responseCode = "204", description = "Success,No content", content = @Content())
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> deleteUserBudget(@PathVariable("budgetid") UUID budgetId) {
        UUID currentUserId = auditor.getCurrentAuditor().get();

        budgetService.deleteUserBudget(currentUserId, budgetId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
