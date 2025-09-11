package dev.afecioru.springlabs.core.todo;

import dev.afecioru.springlabs.core.Error.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public List<ToDo> getAll() {
        return toDoRepository.findAll();
    }

    public ToDo get(Long id) {
        return toDoRepository.findById(id)
            .orElseThrow(() -> new NotFoundError("ToDo not found with ID: " + id));
    }

    public ToDo create(ToDo toDo) {
        toDo.setId(null);

        val newToDo = toDoRepository.save(toDo);
        log.info("Created new ToDo with ID {}", newToDo.getId());
        return newToDo;
    }

    public ToDo update(ToDo toDo) {
        return toDoRepository.findById(toDo.getId())
            .map(existingToDo -> {
                existingToDo.setTitle(toDo.getTitle());
                existingToDo.setDescription(toDo.getDescription());
                existingToDo.setCompleted(toDo.isCompleted());

                val updatedToDo = toDoRepository.save(existingToDo);
                log.info("Updated ToDo with ID {}", updatedToDo.getId());
                return updatedToDo;
            })
            .orElseThrow(() -> new NotFoundError("ToDo not found with ID: " + toDo.getId()));
    }

    public void delete(Long id) {
        toDoRepository.findById(id)
            .ifPresentOrElse(
                existingToDo -> {
                    toDoRepository.deleteById(id);
                    log.info("Deleted ToDo with ID {}", id);
                },
                () -> {
                    throw new NotFoundError("ToDo not found with ID: " + id);
                }
            );
    }

    public void deleteAll() {
        toDoRepository.deleteAll();
        log.info("Deleted all ToDos");
    }
}
