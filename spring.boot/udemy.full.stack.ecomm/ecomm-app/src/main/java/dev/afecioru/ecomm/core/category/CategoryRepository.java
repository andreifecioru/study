package dev.afecioru.ecomm.core.category;

import java.util.Optional;


public interface CategoryRepository {
    CategoryPage findAll(int pageNo, int pageSize, String sortBy, String sortOrder);
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);

    Category save(Category category);

    void deleteById(Long id);
    void deleteAll();
}
