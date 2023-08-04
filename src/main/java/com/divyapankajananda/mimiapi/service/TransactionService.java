package com.divyapankajananda.mimiapi.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.divyapankajananda.mimiapi.dto.TransactionDtoMapper;
import com.divyapankajananda.mimiapi.dto.TransactionRequestDto;
import com.divyapankajananda.mimiapi.dto.TransactionUpdateRequestDto;
import com.divyapankajananda.mimiapi.entity.Budget;
import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.entity.Transaction;
import com.divyapankajananda.mimiapi.entity.TransactionType;
import com.divyapankajananda.mimiapi.exception.ForbiddenActionException;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.TransactionRepository;

@Service
@Lazy
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private TransactionDtoMapper transactionDtoMapper;

    @Transactional
    public Transaction saveUserTransaction(TransactionRequestDto transactionRequestDto){
        Transaction transaction = transactionDtoMapper.toTransaction(transactionRequestDto);

        Category category = categoryService.findCategoryByUserIdAndCategoryId(transaction.getUser().getUserId(), transaction.getCategory().getCategoryId());
        Budget budget = budgetService.findUserBudget(transaction.getUser().getUserId(), transaction.getBudget().getBudgetId());
        
        if(transaction.getType() == TransactionType.EXPENSE){
            Boolean isExpenseBudgetSufficient = budget.getExpenseAmount()+transaction.getAmount()<=budget.getExpenseBudget(); 
            if(!isExpenseBudgetSufficient)
                throw new ForbiddenActionException("Transaction can't be saved since expense budget is not sufficient.");
            else
                budget.setExpenseAmount(budget.getExpenseAmount()+transaction.getAmount());
        }else{
            Boolean isGoalBudgetSufficient = budget.getGoalAmount()+transaction.getAmount()<=budget.getGoalBudget();
            if(!isGoalBudgetSufficient)
                throw new ForbiddenActionException("Transaction can't be saved since goal budget is not sufficient.");
            else
                budget.setGoalAmount(budget.getGoalAmount()+transaction.getAmount());
        }
        budget = budgetService.updateUserBudget(budget);   

        transaction.setCategory(category);
        transaction.setBudget(budget);

        return transactionRepository.save(transaction);
    }    

    public Page<Transaction> findAllUserTransactionsWithPagination(UUID userId, int offset, int size){
        return transactionRepository.findAllByUserUserId(userId, PageRequest.of(offset, size));
    }

    public Page<Transaction> findAllUserTransactionsByBudgetIdWithPagination(UUID userId, UUID budgetId, int offset, int size){
        return transactionRepository.findAllByUserUserIdAndBudgetBudgetId(userId, budgetId, PageRequest.of(offset, size));
    }

    public Boolean transactionByCategoryIdExists(UUID categoryId) {
        Long count = transactionRepository.findAllByCategoryCategoryIdWithLimit(categoryId, 1).count();
        return count != 0;
    }

    public Transaction findUserTransactionByUserUserIDAndTransactionId(UUID userId, UUID transactionId){
        Optional<Transaction> transactionOptional = transactionRepository.findByUserUserIdAndTransactionId(userId, transactionId);

        if(!transactionOptional.isPresent())
        throw new ResourceNotFoundException("Transaction not found.");
        
        return transactionOptional.get();
    }

    @Transactional
    public Transaction updateUserTransaction(UUID userId, UUID transactionId,TransactionUpdateRequestDto transactionUpdateRequestDto){
        Transaction existingTransaction = findUserTransactionByUserUserIDAndTransactionId(userId, transactionId);
        Budget budget = existingTransaction.getBudget();
        
        Transaction transaction = transactionDtoMapper.toTransaction(existingTransaction, transactionUpdateRequestDto);
        Category category = categoryService.findCategoryByUserIdAndCategoryId(transaction.getUser().getUserId(), transaction.getCategory().getCategoryId());
        transaction.setCategory(category);
        
        if(transaction.getType() == TransactionType.EXPENSE){
            Boolean isExpenseBudgetSufficient = budget.getExpenseAmount()-existingTransaction.getAmount()+transaction.getAmount()<=budget.getExpenseBudget(); 
            if(!isExpenseBudgetSufficient)
                throw new ForbiddenActionException("Transaction can't be saved since expense budget is not sufficient.");
            else
                budget.setExpenseAmount(budget.getExpenseAmount()-existingTransaction.getAmount()+transaction.getAmount());
        }else{
            Boolean isGoalBudgetSufficient = budget.getGoalAmount()-existingTransaction.getAmount()+transaction.getAmount()<=budget.getGoalBudget();
            if(!isGoalBudgetSufficient)
                throw new ForbiddenActionException("Transaction can't be saved since goal budget is not sufficient.");
            else
                budget.setGoalAmount(budget.getGoalAmount()-existingTransaction.getAmount()+transaction.getAmount());
        }
        budget = budgetService.updateUserBudget(budget);   
        transaction.setBudget(budget);

        return transactionRepository.save(transaction);
    }
    
    @Transactional
    public void deleteUserTransaction(UUID userId, UUID transactionId) {
        Transaction existingTransaction = findUserTransactionByUserUserIDAndTransactionId(userId, transactionId);
        Budget budget = budgetService.findUserBudget(existingTransaction.getUser().getUserId(), existingTransaction.getBudget().getBudgetId());


        Boolean isTransactionExpenseType = existingTransaction.getType() == TransactionType.EXPENSE;
        if(isTransactionExpenseType){
            budget.setExpenseAmount(budget.getExpenseAmount()-existingTransaction.getAmount());
        }else{
            budget.setGoalAmount(budget.getGoalAmount()-existingTransaction.getAmount());
        }
        budgetService.updateUserBudget(budget);   

        transactionRepository.delete(existingTransaction);
        return;
    }

}
