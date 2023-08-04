package com.divyapankajananda.mimiapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    public Optional<List<Category>> findAllByUserUserId(UUID userId);

    public Optional<Category> findByUserUserIdAndCategoryId(UUID currentUserId, UUID categoryId);
}