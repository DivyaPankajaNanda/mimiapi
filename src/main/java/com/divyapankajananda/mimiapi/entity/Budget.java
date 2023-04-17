package com.divyapankajananda.mimiapi.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="total_budget", nullable = false)
    private Double totalBudget;

    @Column(name="investment_budget", nullable = false)
    private Double investmentBudget;

    @Column(name="expense_budget", nullable = false)
    private Double expenseBudget;

    @Column(name="saving_budget", nullable = false)
    private Double savingBudget;

    @Column(name="investment_amount", nullable = false)
    private Double investmentAmount;

    @Column(name="expense_mount", nullable = false)
    private Double expenseAmount;

    @Column(name="saving_amount", nullable = false)
    private Double savingAmount;

    @Column(name="currency", nullable = false)
    private String currency;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @CreatedBy
    @Column(name="userid", nullable = false, updatable = false)
    private UUID userId;
    
}
