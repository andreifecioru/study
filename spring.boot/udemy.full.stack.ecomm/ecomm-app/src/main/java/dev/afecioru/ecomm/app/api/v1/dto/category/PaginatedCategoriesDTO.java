package dev.afecioru.ecomm.app.api.v1.dto.category;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class PaginatedCategoriesDTO {
    private final List<CategoryDTO> content;
}
