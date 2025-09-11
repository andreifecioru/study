package dev.afecioru.springlabs.core.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private boolean completed;
}
