package dev.afecioru.ecomm.external.postgres;

import dev.afecioru.ecomm.core.category.Category;
import dev.afecioru.ecomm.core.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static dev.afecioru.ecomm.external.postgres.CategoryEntity.EntityMapper;

@Component
@RequiredArgsConstructor
public class PostgresCategoryRepository implements CategoryRepository {
    private final CategoryDB categoryDB;

    @Override
    public List<Category> findAll() {
        return categoryDB.findAll().stream()
                .map(EntityMapper::fromEntity)
                .toList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryDB.findById(id)
                .map(EntityMapper::fromEntity);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryDB.findByName(name)
                .map(EntityMapper::fromEntity);
    }

    @Override
    public Category save(Category category) {
        return EntityMapper.fromEntity(
            categoryDB.save(EntityMapper.toEntity(category))
        );
    }

    @Override
    public void deleteById(Long id) {
        categoryDB.deleteById(id);
    }

    @Override
    public void deleteAll() {
        categoryDB.deleteAll();
    }
}
