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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divyapankajananda.mimiapi.dto.TransactionRequestDto;
import com.divyapankajananda.mimiapi.entity.Transaction;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("mimiapi/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    public ResponseEntity<Object> saveTransaction(@RequestBody @Valid TransactionRequestDto transactionRequestDto) {
        Transaction transaction = transactionService.saveTransaction(transactionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transaction);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAllUserTransactions(@RequestParam int offset,@RequestParam int size) {
        UUID currentUserId = auditor.getCurrentAuditor().get();

        Page<Transaction> transactions = transactionService.findAllUserTransactionsWithPagination(currentUserId,offset,size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(transactions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTransaction(@PathVariable UUID id, @RequestBody @Valid TransactionRequestDto transactionRequestDto) throws ResourceNotFoundException {
        Optional<Transaction> existing_transaction = transactionService.findTransactionById(id);

        if(existing_transaction.isPresent()){
            Transaction transaction = transactionService.updateTransaction(id,transactionRequestDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(transaction);
        }else
            throw new ResourceNotFoundException(String.format("Transaction with %s doesn't exist.",id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable UUID id) throws ResourceNotFoundException {
        Optional<Transaction> existing_transaction = transactionService.findTransactionById(id);

        if(existing_transaction.isPresent()){
            transactionService.deleteTransaction(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }else
            throw new ResourceNotFoundException(String.format("Transaction with %s doesn't exist.",id));

    }
    
}
