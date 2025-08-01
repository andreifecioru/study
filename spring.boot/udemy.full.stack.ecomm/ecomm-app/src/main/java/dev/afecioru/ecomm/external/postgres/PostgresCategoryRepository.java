package dev.afecioru.ecomm.external.postgres;

import dev.afecioru.ecomm.core.category.Category;
import dev.afecioru.ecomm.core.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class PostgresCategoryRepository implements CategoryRepository {
    private final CategoryDB categoryDB;
    private final ModelMapper modelMapper;

    @Override
    public List<Category> findAll(int pageNo, int pageSize) {
        val pageable = PageRequest.of(pageNo, pageSize);
        val categoryPage = categoryDB.findAll(pageable);

        return categoryPage.getContent().stream()
            .map(entity -> modelMapper.map(entity, Category.class))
            .toList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryDB.findById(id)
                .map(entity -> modelMapper.map(entity, Category.class));
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryDB.findByName(name)
                .map(entity -> modelMapper.map(entity, Category.class));
    }

    @Override
    public Category save(Category category) {
        val entity = categoryDB.save(modelMapper.map(category, CategoryEntity.class));
        return modelMapper.map(entity, Category.class);
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
