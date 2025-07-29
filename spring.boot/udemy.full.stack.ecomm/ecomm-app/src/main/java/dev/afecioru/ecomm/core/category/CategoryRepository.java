package dev.afecioru.ecomm.core.category;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);

    Category save(Category category);

    void deleteById(Long id);
    void deleteAll();
}
