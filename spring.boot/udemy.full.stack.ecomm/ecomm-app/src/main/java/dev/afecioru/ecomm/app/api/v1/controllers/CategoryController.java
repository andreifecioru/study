package dev.afecioru.ecomm.app.api.v1.controllers;

import dev.afecioru.ecomm.app.api.v1.dto.category.PaginatedCategoriesDTO;
import dev.afecioru.ecomm.app.api.v1.dto.category.CategoryDTO;
import dev.afecioru.ecomm.core.Error.*;
import dev.afecioru.ecomm.core.category.Category;
import dev.afecioru.ecomm.core.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<PaginatedCategoriesDTO> getAllCategories(
        @RequestParam(name = "page", defaultValue = "0") int pageNo,
        @RequestParam(name = "size", defaultValue = "10") int pageSize
    ) {
        List<Category> categories = categoryService.getAll(pageNo, pageSize);
        val response = new PaginatedCategoriesDTO(
            categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        val category = categoryService.get(id);
        val response = modelMapper.map(category, CategoryDTO.class);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        val savedCategory = categoryService.create(modelMapper.map(categoryDTO, Category.class));
        val response = modelMapper.map(savedCategory, CategoryDTO.class);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        val category = modelMapper.map(categoryDTO, Category.class);
        category.setId(id);
        val updatedCategory = categoryService.update(category);
        val response = modelMapper.map(updatedCategory, CategoryDTO.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAllCategories() {
        categoryService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
