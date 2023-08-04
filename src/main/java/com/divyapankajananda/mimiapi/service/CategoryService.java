package com.divyapankajananda.mimiapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.dto.CategoryDtoMapper;
import com.divyapankajananda.mimiapi.dto.CategoryRequestDto;
import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.entity.Icon;
import com.divyapankajananda.mimiapi.exception.ForbiddenActionException;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.CategoryRepository;

@Service
@Lazy
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDtoMapper categoryDtoMapper;

    @Autowired
    private IconService iconService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private GoalService goalService;

    public Category saveUserCategory(CategoryRequestDto categoryRequestDto) {
        Icon icon = iconService.findByIconId(categoryRequestDto.getIconId());
        Category category = categoryDtoMapper.toCategory(categoryRequestDto);
        category.setIcon(icon);

        return categoryRepository.save(category);
    }

    public List<Category> findAllCategoryForUser(UUID currentUserId) {
        Optional<List<Category>> categoriesOptional = categoryRepository.findAllByUserUserId(currentUserId);
        
        if(!categoriesOptional.isPresent() || categoriesOptional.get().isEmpty())
        throw new ResourceNotFoundException("Categories not found.");

        return categoriesOptional.get();
    }

    public Category findCategoryByUserIdAndCategoryId(UUID currentUserId, UUID categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findByUserUserIdAndCategoryId(currentUserId, categoryId);
        
        if(!categoryOptional.isPresent())
        throw new ResourceNotFoundException("Category not found.");

        return categoryOptional.get();
    }

    public Category updateUserCategory(UUID userId, UUID categoryId, CategoryRequestDto categoryRequestDto) {
        Optional<Category> categoryOptional = categoryRepository.findByUserUserIdAndCategoryId(userId, categoryId);
        
        if(!categoryOptional.isPresent())
        throw new ResourceNotFoundException("Category not found.");

        Icon icon = iconService.findByIconId(categoryRequestDto.getIconId());

        Category category = categoryDtoMapper.toCategory(categoryRequestDto);
        category.setCategoryId(categoryId);
        category.setIcon(icon);

        return categoryRepository.save(category);
    }

    public void deleteUserCategory(UUID currentUserId, UUID categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findByUserUserIdAndCategoryId(currentUserId, categoryId);
        
        if(!categoryOptional.isPresent())
        throw new ResourceNotFoundException("Category not found.");
        Category existingCategory = categoryOptional.get();

        if(transactionService.transactionByCategoryIdExists(categoryId))
        throw new ForbiddenActionException("Category can't be deleted since it is mapped to at least one transaction.");

        if(goalService.goalByCategoryIdExists(categoryId))
        throw new ForbiddenActionException("Category can't be deleted since it is mapped to at least one goal.");

        categoryRepository.delete(existingCategory);
        return;
    }
}
