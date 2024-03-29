package com.divyapankajananda.mimiapi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @Column(name="budget_id")
    private UUID budgetId;

    @Column(name="total_budget", nullable = false)
    private Double totalBudget;

    @Column(name="expense_budget", nullable = false)
    private Double expenseBudget;

    @Column(name="expense_amount", nullable = false)
    private Double expenseAmount;

    @Column(name="goal_budget", nullable = false)
    private Double goalBudget;

    @Column(name="goal_amount", nullable = false)
    private Double goalAmount;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @JsonIgnore
    @OneToMany(mappedBy = "budget",fetch = FetchType.LAZY)
    private List<Transfer> transfers;

    @JsonIgnore
    @OneToMany(mappedBy = "budget",fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false, updatable = false)
    private User user;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
}
