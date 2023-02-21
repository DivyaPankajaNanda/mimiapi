package com.divyapankajananda.mimiapi.entity;

import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "custom_category")
public class CustomCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    
    @Column(name = "title", nullable = false)
    private String title;

    
    @Column(name = "description")
    private String description;

    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iconid", referencedColumnName = "id")
    private Icon icon;

    @CreatedBy
    @Column(name="userid", nullable = false, updatable = false)
    private UUID userId;

}
