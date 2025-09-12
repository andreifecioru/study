package dev.afecioru.springlabs.app.api.v1.controllers;

import dev.afecioru.springlabs.app.api.dto.todo.ToDoDTO;
import dev.afecioru.springlabs.core.todo.ToDo;
import dev.afecioru.springlabs.core.todo.ToDoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
public class ToDoController {
    private final ToDoService toDoService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<ToDoDTO>> getAll() {
        val toDos = toDoService.getAll().stream().map(toDo ->
            modelMapper.map(toDo, ToDoDTO.class)
        ).toList();

        return ResponseEntity.ok(toDos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoDTO> get(@PathVariable Long id) {
        val toDo = modelMapper.map(toDoService.get(id), ToDoDTO.class);
        return ResponseEntity.ok(toDo);
    }

    @PostMapping("")
    public ResponseEntity<ToDoDTO> create(@Valid @RequestBody ToDoDTO toDoDTO) {
        val newToDo = toDoService.create(modelMapper.map(toDoDTO, ToDo.class));
        val response = modelMapper.map(newToDo, ToDoDTO.class);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoDTO> update(@PathVariable Long id, @Valid @RequestBody ToDoDTO toDoDTO) {
        val toDo = modelMapper.map(toDoDTO, ToDo.class);
        toDo.setId(id);

        val updatedToDo = toDoService.update(toDo);
        val response = modelMapper.map(updatedToDo, ToDoDTO.class);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        toDoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAll() {
        toDoService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
