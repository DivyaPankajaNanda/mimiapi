package com.divyapankajananda.mimiapi.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.PaymentDtoMapper;
import com.divyapankajananda.mimiapi.dto.PaymentRequestDto;
import com.divyapankajananda.mimiapi.entity.Payment;
import com.divyapankajananda.mimiapi.repository.PaymentRepository;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentDtoMapper paymentDtoMapper;

    public Payment savePayment(PaymentRequestDto paymentRequestDto){
        Payment payment = paymentDtoMapper.toPayment(paymentRequestDto);
        return paymentRepository.save(payment);
    }    

    public List<Payment> saveAllPayments(List<PaymentRequestDto> paymentRequestDtoList){
        List<Payment> paymentList = paymentRequestDtoList.stream().map(paymentRequestDto->paymentDtoMapper.toPayment(paymentRequestDto)).collect(Collectors.toList());
        return paymentRepository.saveAll(paymentList);
    }

    public Page<Payment> findAllUserPaymentsWithPagination(UUID userId, int offset, int size){
        return paymentRepository.findAllByUserId(userId, PageRequest.of(offset, size));
    }    

}
