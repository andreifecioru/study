package dev.afecioru.ecomm.app.config.core;

import dev.afecioru.ecomm.app.api.v1.dto.category.CategoryDTO;
import dev.afecioru.ecomm.app.api.v1.dto.category.PaginatedCategoriesDTO;
import dev.afecioru.ecomm.core.category.Category;
import dev.afecioru.ecomm.core.category.CategoryPage;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MMaper {
    @Bean
    public ModelMapper modelMapper() {
        val mapper = new ModelMapper();

        mapper.createTypeMap(CategoryPage.class, PaginatedCategoriesDTO.class)
            .addMappings(mapping -> mapping.using(ctx -> {
                List<Category> categories = (List<Category>) ctx.getSource();
                return categories.stream()
                    .map(category -> mapper.map(category, CategoryDTO.class))
                    .toList();
            }).map(CategoryPage::getContent, PaginatedCategoriesDTO::setContent));

        return mapper;
    }
}
