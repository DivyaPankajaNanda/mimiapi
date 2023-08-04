package com.divyapankajananda.mimiapi.controller.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divyapankajananda.mimiapi.dto.CategoryRequestDto;
import com.divyapankajananda.mimiapi.dto.CustomExceptionDto;
import com.divyapankajananda.mimiapi.entity.Category;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.service.CategoryService;
import com.divyapankajananda.mimiapi.util.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.API_V1_PREFIX+"category")
@Tag(name = "Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuditorAware<UUID> auditor;

    @PostMapping("/")
    @Operation(summary = "Save category.")
    @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> saveUserCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) throws ResourceNotFoundException{
        Category category = categoryService.saveUserCategory(categoryRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(category);
    }

    @GetMapping("/")
    @Operation(summary = "Get all categories belonging to the current user.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Category.class))))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> findAllCategoryForUser() throws ResourceNotFoundException {
        UUID currentUserId = auditor.getCurrentAuditor().get();
        List<Category> categories = categoryService.findAllCategoryForUser(currentUserId);
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(categories);
    }

    @PutMapping("/{categoryid}")
    @Operation(summary = "Update category.")
    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> updateUserCategory(@PathVariable("categoryid") @Valid UUID categoryId,@RequestBody @Valid CategoryRequestDto categoryRequestDto) throws ResourceNotFoundException{
        UUID currentUserId = auditor.getCurrentAuditor().get();
        Category category = categoryService.updateUserCategory(currentUserId, categoryId, categoryRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(category);        
    }

    @DeleteMapping("/{categoryid}")
    @Operation(summary = "Delete category.")
    @ApiResponse(responseCode = "204", description = "Success, No content", content = @Content())
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomExceptionDto.class)))
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryid") @Valid UUID categoryId) throws ResourceNotFoundException{
        UUID currentUserId = auditor.getCurrentAuditor().get();
        categoryService.deleteUserCategory(currentUserId,categoryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(null);        
    }
    
}
