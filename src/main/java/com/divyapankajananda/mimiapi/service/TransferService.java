package com.divyapankajananda.mimiapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.divyapankajananda.mimiapi.dto.TransferDtoMapper;
import com.divyapankajananda.mimiapi.dto.TransferRequestDto;
import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.entity.Transfer;
import com.divyapankajananda.mimiapi.exception.ForbiddenActionException;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.TransferRepository;

@Service
public class TransferService {
    
    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransferDtoMapper transferDtoMapper;

    @Autowired
    private GoalService goalService;

    @Autowired
    private BudgetService budgetService;

    @Transactional
    public Transfer saveUserTransfer(TransferRequestDto transferRequestDto){
        Transfer transfer = transferDtoMapper.toTransfer(transferRequestDto);
        Budget budget = budgetService.findUserBudget(transfer.getUser().getUserId(), transfer.getBudget().getBudgetId());
        Goal goal = goalService.findGoalByUserIdAndGoalId(transfer.getUser().getUserId(), transfer.getGoal().getGoalId());
        
        Boolean isGoalBudgetSufficient = (budget.getGoalAmount()+transfer.getAmount()<= budget.getGoalBudget());
        if(!isGoalBudgetSufficient)
            throw new ForbiddenActionException("Transfer can't be made since goal budget is not sufficient.");
        else
            budget.setGoalAmount(budget.getGoalAmount()+transfer.getAmount());

        budget = budgetService.updateUserBudget(budget);

        Boolean isGoalAmountFulfilled = goal.getAmountRequired() == goal.getAmountSaved();
        Boolean isGoalClaimed = goal.isClaimed();
        if(isGoalClaimed)
            throw new ForbiddenActionException("Transfer can't be made since goal is already claimed.");
        else if(isGoalAmountFulfilled)
            throw new ForbiddenActionException("Transfer can't be made since required amount for the goal is already fulfilled.");
        else
            goal.setAmountSaved(goal.getAmountSaved()+transfer.getAmount());
        
        goal = goalService.saveUserGoal(goal);

        transfer.setGoal(goal);
        transfer.setBudget(budget);
        
        return transferRepository.save(transfer);
    }

    public Page<Transfer> findAllUserTransferByBudgetIdWithPagination(UUID userId, UUID budgetId, int offset, int size){
        return transferRepository.findAllByUserUserIdAndBudgetBudgetId(userId, budgetId, PageRequest.of(offset, size));
    }

    public Page<Transfer> findAllUserTransferByGoalIdWithPagination(UUID userId, UUID goalId, int offset, int size){
        return transferRepository.findAllByUserUserIdAndGoalGoalId(userId, goalId, PageRequest.of(offset, size));
    }

    @Transactional
    public void deleteUserTransfer(UUID userId, UUID transferId){
        Optional<Transfer> transferOptional = transferRepository.findByUserUserIdAndTransferId(userId, transferId);
        if(!transferOptional.isPresent())
        throw new ResourceNotFoundException("Transfer not found.");

        Transfer transfer = transferOptional.get();
        Budget budget = budgetService.findUserBudget(transfer.getUser().getUserId(), transfer.getBudget().getBudgetId());
        Goal goal = goalService.findGoalByUserIdAndGoalId(transfer.getUser().getUserId(), transfer.getGoal().getGoalId());
        
        budget.setGoalAmount(budget.getGoalAmount()-transfer.getAmount());
        goal.setAmountSaved(goal.getAmountSaved()-transfer.getAmount());

        transferRepository.delete(transfer);
        return;
    }

}
