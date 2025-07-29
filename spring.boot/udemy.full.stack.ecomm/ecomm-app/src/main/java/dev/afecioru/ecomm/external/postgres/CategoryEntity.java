package dev.afecioru.ecomm.external.postgres;

import dev.afecioru.ecomm.core.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public interface EntityMapper {
        static Category fromEntity(CategoryEntity entity) {
            Category category = new Category();

            category.setId(entity.getId());
            category.setName(entity.getName());

            return category;
        }

        static CategoryEntity toEntity(Category category) {
            CategoryEntity entity = new CategoryEntity();

            entity.setId(category.getId());
            entity.setName(category.getName());

            return entity;
        }
    }
}
