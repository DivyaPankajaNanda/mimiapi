package com.divyapankajananda.mimiapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.CustomCategory;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory, UUID> {
    List<CustomCategory> findAllByUserId(UUID userId);   
}