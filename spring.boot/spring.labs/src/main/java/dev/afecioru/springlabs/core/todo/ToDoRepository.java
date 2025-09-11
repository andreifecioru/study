package dev.afecioru.springlabs.core.todo;

import java.util.List;
import java.util.Optional;

public interface ToDoRepository {
    List<ToDo> findAll();
    Optional<ToDo> findById(Long id);

    ToDo save(ToDo toDo);

    void deleteById(Long id);
    void deleteAll();
}
