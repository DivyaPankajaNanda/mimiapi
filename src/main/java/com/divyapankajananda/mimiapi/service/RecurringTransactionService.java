package com.divyapankajananda.mimiapi.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.RecurringTransactionDtoMapper;
import com.divyapankajananda.mimiapi.dto.RecurringTransactionRequestDto;
import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.entity.RecurringTransaction;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.RecurringTransactionRepository;


@Service
public class RecurringTransactionService {
    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    private RecurringTransactionDtoMapper recurringTransactionDtoMapper;
    
    @Autowired
    private CategoryService categoryService;

    public RecurringTransaction saveUserRecurringTransaction(RecurringTransactionRequestDto recurringTransactionRequestDto) {
        RecurringTransaction recurringTransaction = recurringTransactionDtoMapper.toRecurringTransaction(recurringTransactionRequestDto);
        Category category = categoryService.findCategoryByUserIdAndCategoryId(recurringTransaction.getUser().getUserId(), recurringTransaction.getCategory().getCategoryId());
        recurringTransaction.setCategory(category);
        return recurringTransactionRepository.save(recurringTransaction);
    }

    public Page<RecurringTransaction> findAllUserRecurringTransactionsWithPagination(UUID userId, int offset, int size){
        return recurringTransactionRepository.findAllByUserUserId(userId, PageRequest.of(offset, size));
    }
    
    public RecurringTransaction updateUserRecurringTransaction(UUID userId, UUID transactionId, RecurringTransactionRequestDto recurringTransactionRequestDto) {
        Optional<RecurringTransaction> recurringTransactionOptional = recurringTransactionRepository.findByUserUserIdAndTransactionId(userId, transactionId);
        if(!recurringTransactionOptional.isPresent())
        throw new ResourceNotFoundException("Recurring transaction not found.");

        RecurringTransaction recurringTransaction = recurringTransactionDtoMapper.toRecurringTransaction(recurringTransactionRequestDto);
        Category category = categoryService.findCategoryByUserIdAndCategoryId(recurringTransaction.getUser().getUserId(), recurringTransaction.getCategory().getCategoryId());
        recurringTransaction.setCategory(category);
        return recurringTransactionRepository.save(recurringTransaction);
    }

    public void deleteUserRecurringTransaction(UUID userId, UUID transactionId){
        Optional<RecurringTransaction> recurringTransactionOptional = recurringTransactionRepository.findByUserUserIdAndTransactionId(userId, transactionId);
        if(!recurringTransactionOptional.isPresent())
        throw new ResourceNotFoundException("Recurring transaction not found.");

        recurringTransactionRepository.delete(recurringTransactionOptional.get());
        return;
    }
}
