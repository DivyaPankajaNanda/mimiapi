package com.divyapankajananda.mimiapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.RecurringTransferDtoMapper;
import com.divyapankajananda.mimiapi.dto.RecurringTransferRequestDto;
import com.divyapankajananda.mimiapi.entity.Goal;
import com.divyapankajananda.mimiapi.entity.RecurringTransfer;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.RecurringTransferRepository;


@Service
public class RecurringTransferService {
    
    @Autowired
    private RecurringTransferRepository recurringTransferRepository;

    @Autowired
    private RecurringTransferDtoMapper recurringTransferDtoMapper;

    @Autowired
    private GoalService goalService;

    public RecurringTransfer saveUserRecurringTransfer(RecurringTransferRequestDto recurringTransferRequestDto){
        RecurringTransfer recurringTransfer = recurringTransferDtoMapper.toRecurringTransfer(recurringTransferRequestDto);
        Goal goal = goalService.findGoalByUserIdAndGoalId(recurringTransfer.getUser().getUserId(), recurringTransfer.getGoal().getGoalId());
        recurringTransfer.setGoal(goal);
        return recurringTransferRepository.save(recurringTransfer);
    }

    public Page<RecurringTransfer> findAllUserRecurringTransferByGoalIdWithPagination(UUID userId, UUID goalId, int offset, int size){
        return recurringTransferRepository.findAllByUserUserIdAndGoalGoalId(userId, goalId, PageRequest.of(offset, size));
    }

    public RecurringTransfer updateUserRecurringTransfer(UUID userId, UUID transferId, RecurringTransferRequestDto recurringTransferRequestDto){
        Optional<RecurringTransfer> recurringTransferOptional = recurringTransferRepository.findByUserUserIdAndTransferId(userId, transferId);
        if(!recurringTransferOptional.isPresent())
        throw new ResourceNotFoundException("Recurring Transfer not found.");

        RecurringTransfer recurringTransfer = recurringTransferDtoMapper.toRecurringTransfer(recurringTransferRequestDto);
        recurringTransfer.setTransferId(transferId);
        recurringTransferRepository.save(recurringTransfer);
        return recurringTransfer;
    }

    public void deleteUserRecurringTransfer(UUID userId, UUID transferId){
        Optional<RecurringTransfer> recurringTransferOptional = recurringTransferRepository.findByUserUserIdAndTransferId(userId, transferId);
        if(!recurringTransferOptional.isPresent())
        throw new ResourceNotFoundException("Recurring Transfer not found.");

        recurringTransferRepository.delete(recurringTransferOptional.get());
        return;
    }
}
