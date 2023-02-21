package com.divyapankajananda.mimiapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.Icon;

public interface IconRepository extends JpaRepository<Icon, Long> {
    
}