package dev.afecioru.ecomm.app.config.core;

import dev.afecioru.ecomm.core.category.CategoryService;
import dev.afecioru.ecomm.external.postgres.PostgresCategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfig {
    @Bean
    CategoryService categoryService(
        PostgresCategoryRepository categoryRepository
    ) {
        return new CategoryService(categoryRepository);
    }
}
