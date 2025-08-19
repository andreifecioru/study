package dev.afecioru.ecomm.external.postgres;

import dev.afecioru.ecomm.core.category.Category;
import dev.afecioru.ecomm.core.category.CategoryPage;
import dev.afecioru.ecomm.core.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class PostgresCategoryRepository implements CategoryRepository {
    private final CategoryDB categoryDB;
    private final ModelMapper modelMapper;

    @Override
    public CategoryPage findAll(int pageNo, int pageSize, String sortBy, String sortOrder) {
        Sort sortCriteria = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        val pageable = PageRequest.of(pageNo, pageSize, sortCriteria);
        val categoryPage = categoryDB.findAll(pageable);

        val content = categoryPage.getContent().stream()
            .map(entity -> modelMapper.map(entity, Category.class))
            .toList();

        return CategoryPage.builder()
            .content(content)
            .pageNo(categoryPage.getNumber())
            .pageSize(categoryPage.getSize())
            .totalElements(categoryPage.getTotalElements())
            .totalPages(categoryPage.getTotalPages())
            .lastPage(categoryPage.isLast())
            .build();
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
