package dev.afecioru.ecomm.core.category;

import dev.afecioru.ecomm.core.Error.GenericError;
import dev.afecioru.ecomm.core.Error.InvalidOperationError;
import dev.afecioru.ecomm.core.Error.NotFoundError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryPage getAll(int pageNo, int pageSize, String sortBy, String sortOrder) {
        return categoryRepository.findAll(pageNo, pageSize, sortBy, sortOrder);
    }

    public Category get(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundError("Category not found with id: " + id));
    }

    public Category create(Category category) {
        category.setId(null);

        val newCategory = save(category);
        log.info("Created category with ID: {}", newCategory.getId());

        return newCategory;
    }

    public Category update(Category category) {
        return categoryRepository.findById(category.getId())
            .map(existingCategory -> {
                existingCategory.setName(category.getName());

                Category updatedCategory = save(existingCategory);
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

    private Category save(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Unable to save category: {}", e.getMessage());
            if (e.getMessage().contains("violates unique constraint")) {
                throw new InvalidOperationError("Category with name: " + category.getName() + " already exists");
            } else {
                throw new GenericError("Unable to create category: " + e.getMessage());
            }
        }
    }
}
