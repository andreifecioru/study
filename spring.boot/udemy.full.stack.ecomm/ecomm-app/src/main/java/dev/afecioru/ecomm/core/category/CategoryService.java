package dev.afecioru.ecomm.core.category;

import dev.afecioru.ecomm.core.Error.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> get(Long id) {
        return categoryRepository.findById(id);
    }

    public Category create(Category category) {
        category.setId(null);

        Category newCategory = categoryRepository.save(category);
        log.info("Created category with ID: {}", newCategory.getId());
        return newCategory;
    }

    public Category update(Category category) {
        return categoryRepository.findById(category.getId())
            .map(existingCategory -> {
                existingCategory.setName(category.getName());

                Category updatedCategory = categoryRepository.save(existingCategory);
                log.info("Updated category with ID: {}", updatedCategory.getId());
                return updatedCategory;
            })
            .orElseThrow(() -> {
                String errMsg = String.format("Unable to find category with ID: %d", category.getId());
                log.error(errMsg);

                return new NotFoundError(errMsg);
            });
    }

    public void delete(Long id) {
        categoryRepository.findById(id)
            .ifPresentOrElse(
                existingCategory -> {
                    categoryRepository.deleteById(existingCategory.getId());
                    log.info("Deleted category with ID: {}", existingCategory.getId());
                },
                () -> {
                    String errMsg = String.format("Unable to find category with ID: %d", id);
                    log.error(errMsg);
                    throw new NotFoundError(errMsg);
                }
            );
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
