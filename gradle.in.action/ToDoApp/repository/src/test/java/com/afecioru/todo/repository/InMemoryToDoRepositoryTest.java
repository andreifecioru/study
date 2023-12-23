package com.afecioru.todo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.afecioru.todo.models.ToDoItem;


public class InMemoryToDoRepositoryTest {
  private ToDoRepository repository;

  @BeforeEach
  public void setUp() {
    repository = new InMemoryToDoRepository();
  }

  @Test
  public void insertToDoItem() {
    ToDoItem newItem = ToDoItem.builder()
        .name("Write unit-tests")
        .build();
    Long newId = repository.insert(newItem);
    assertNotNull(newId);

    ToDoItem savedItem = repository.findById(newId);
    assertNotNull(savedItem);
    assertEquals(newItem, savedItem);
  }
}
