package com.divyapankajananda.mimiapi.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
@Table(name = "recurring_payment")
public class RecurringPayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "asset_url")
    private String assetUrl;
    
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    // days
    @Column(name = "frequency", nullable = false)
    private int frequency;
    
    @Column(name = "trigger_date")
    private LocalDate triggerDate;
    
    @Column(name = "categoryid", nullable = false)
    private UUID categoryId;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "automated", nullable = false)
    private boolean automated;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name="userid", nullable = false, updatable = false)
    private UUID userId;
    

}
