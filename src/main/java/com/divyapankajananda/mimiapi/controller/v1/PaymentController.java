package com.divyapankajananda.mimiapi.controller.v1;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divyapankajananda.mimiapi.dto.PaymentRequestDto;
import com.divyapankajananda.mimiapi.entity.Payment;
import com.divyapankajananda.mimiapi.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("mimiapi/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    public ResponseEntity<Object> savePayment(@RequestBody @Valid PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentService.savePayment(paymentRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(payment);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAllUserPayment(@RequestParam int offset,@RequestParam int size) {
        UUID currentUserId = auditor.getCurrentAuditor().get();

        Page<Payment> payments = paymentService.findAllUserPaymentsWithPagination(currentUserId,offset,size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(payments);
    }

    
}
