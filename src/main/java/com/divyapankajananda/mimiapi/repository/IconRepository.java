package com.divyapankajananda.mimiapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.Icon;

public interface IconRepository extends JpaRepository<Icon, Long> {

    public Optional<Icon> findByIconId(long iconId);
    
}