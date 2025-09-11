package dev.afecioru.springlabs.external.postgres;

import dev.afecioru.springlabs.core.todo.ToDo;
import dev.afecioru.springlabs.core.todo.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostgresToDoRepository implements ToDoRepository {
    private final ToDoDB toDoDB;
    private final ModelMapper modelMapper;


    @Override
    public List<ToDo> findAll() {
        return toDoDB.findAll().stream().map(entity ->
            modelMapper.map(entity, ToDo.class)
        ).toList();
    }

    @Override
    public Optional<ToDo> findById(Long id) {
        return toDoDB.findById(id).map(entity ->
            modelMapper.map(entity, ToDo.class)
        );
    }

    @Override
    public ToDo save(ToDo toDo) {
        ToDoEntity entity = modelMapper.map(toDo, ToDoEntity.class);
        val savedEntity = toDoDB.save(entity);
        return modelMapper.map(savedEntity, ToDo.class);
    }

    @Override
    public void deleteById(Long id) {
        toDoDB.deleteById(id);
    }

    @Override
    public void deleteAll() {
        toDoDB.deleteAll();
    }
}
