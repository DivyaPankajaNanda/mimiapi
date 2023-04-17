package com.divyapankajananda.mimiapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.TransactionDtoMapper;
import com.divyapankajananda.mimiapi.dto.TransactionRequestDto;
import com.divyapankajananda.mimiapi.entity.Transaction;
import com.divyapankajananda.mimiapi.repository.TransactionRepository;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionDtoMapper transactionDtoMapper;

    public Transaction saveTransaction(TransactionRequestDto transactionRequestDto){
        Transaction transaction = transactionDtoMapper.toTransaction(transactionRequestDto);
        return transactionRepository.save(transaction);
    }    

    public List<Transaction> saveAllTransactions(List<TransactionRequestDto> transactionRequestDtoList){
        List<Transaction> transactionList = transactionRequestDtoList.stream().map(transactionRequestDto->transactionDtoMapper.toTransaction(transactionRequestDto)).collect(Collectors.toList());
        return transactionRepository.saveAll(transactionList);
    }

    public Page<Transaction> findAllUserTransactionsWithPagination(UUID userId, int offset, int size){
        return transactionRepository.findAllByUserId(userId, PageRequest.of(offset, size));
    }

    public Optional<Transaction> findTransactionById(UUID transactionId){
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        return transaction;
    }

    public Transaction updateTransaction(UUID transactionId,TransactionRequestDto transactionRequestDto){
        Transaction transaction = Transaction.builder()
                                    .id(transactionId)
                                    .title(transactionRequestDto.getTitle())
                                    .description(transactionRequestDto.getDescription())
                                    .amount(transactionRequestDto.getAmount())
                                    .build();
        transactionRepository.save(transaction);
        return transaction;
    }
    
    public void deleteTransaction(UUID transactionId) {
        transactionRepository.deleteById(transactionId);
        return;
    }

}
