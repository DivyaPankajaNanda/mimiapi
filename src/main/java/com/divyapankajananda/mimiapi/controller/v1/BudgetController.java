package com.divyapankajananda.mimiapi.controller.v1;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
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
import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.exception.ForbiddenActionException;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.service.BudgetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("mimiapi/v1/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    public ResponseEntity<Object> saveBudget(@RequestBody @Valid BudgetRequestDto budgetRequestDto) throws ForbiddenActionException {
        LocalDate startDate = budgetRequestDto.getStartDate();
        LocalDate endDate = budgetRequestDto.getEndDate();
        UUID currentUserId = auditor.getCurrentAuditor().get();

        Optional<List<Budget>> overlappingBudgets = budgetService.findAllOverlappingUserBudgets(startDate, endDate, currentUserId);

        if (overlappingBudgets.isPresent() && overlappingBudgets.get().size() > 0) {
            throw new ForbiddenActionException(
                    String.format("Overlapping budgets present between %s and %s", startDate, endDate));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(budgetService.saveBudget(budgetRequestDto));
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAllBudgetsBetweenStartAndEndDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws ResourceNotFoundException {

        UUID currentUserId = auditor.getCurrentAuditor().get();
        Optional<List<Budget>> budgets = budgetService.findAllUserBudgetsBetweenStartAndEndDate(startDate, endDate,
                currentUserId);

        if(!budgets.isPresent() || budgets.get().size()<=0){
            throw new ResourceNotFoundException("No budget found");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(budgets.get());
    }

    @PutMapping("/{budgetid}")
    public ResponseEntity<Object> updateBudget(@PathVariable UUID budgetid, @RequestBody @Valid BudgetRequestDto budgetRequestDto) throws ForbiddenActionException, ResourceNotFoundException {
       
        UUID currentUserId = auditor.getCurrentAuditor().get();
        Optional<Budget> budget = budgetService.findBudget(budgetid);

        if (!budget.isPresent()) {
            throw new ResourceNotFoundException("Budget not found");
        }

        if (!budget.get().getUserId().equals(currentUserId)) {
            throw new ForbiddenActionException("Budget is not created by current user *****"+budget.get().getUserId()+"*********"+currentUserId);
        }
        Budget updatedBudget = budgetService.updateBudget(budgetid, budgetRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedBudget);
    }

    @DeleteMapping("/{budgetid}")
    public ResponseEntity<Object> deleteBudget(@PathVariable UUID budgetid)
            throws ResourceNotFoundException, ForbiddenActionException {
        UUID currentUserId = auditor.getCurrentAuditor().get();

        Optional<Budget> budget = budgetService.findBudget(budgetid);

        if (!budget.isPresent()) {
            throw new ResourceNotFoundException("Budget not found");
        }

        if (!budget.get().getUserId().equals(currentUserId)) {
            throw new ForbiddenActionException("Budget is not created by current user");
        }

        budgetService.deleteBudget(budgetid);

        return ResponseEntity.status(HttpStatus.OK)
                .body(null);
    }
}
