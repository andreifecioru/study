package com.afecioru.todo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToDoItem implements Comparable<ToDoItem> {
    @Builder.Default private Long id = 0L;
    private String name;
    @Builder.Default private boolean completed = false;

    @Override
    public int compareTo(ToDoItem other) {
        return id.compareTo(other.id);
    }
}
