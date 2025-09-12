package dev.afecioru.springlabs.app.api.dto.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoDTO {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private boolean completed = false;
}
