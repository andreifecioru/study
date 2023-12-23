package com.afecioru.todo.console.utils;

import com.afecioru.todo.models.ToDoItem;
import com.afecioru.todo.repository.InMemoryToDoRepository;
import com.afecioru.todo.repository.ToDoRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

public class CommandLineInputHandler {

  private final ToDoRepository repository = new InMemoryToDoRepository();

  public boolean handleInput(CommandLineInput input) {
    if (input == null) {
      handleUnknownInput();
      return false;
    } else {
      return switch (input) {
        case FIND_ALL -> {
          printAllItems();
          yield false;
        }
        case FIND_BY_ID -> {
          printItemWithId();
          yield false;
        }
        case INSERT -> {
          createItem();
          yield false;
        }
        case UPDATE -> {
          updateItem();
          yield false;
        }
        case DELETE -> {
          deleteItem();
          yield false;
        }
        case EXIT -> true;
      };
    }
  }

  public String readInput(String prompt) {
    if (System.console() != null) {
      return System.console().readLine(prompt);
    }

    System.out.print(prompt);
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in)
    );

    try {
      return reader.readLine();
    } catch (IOException ex) {
      System.out.println(MessageFormat.format("Failed to read user input. {0}", ex.getMessage()));
      return "";
    }
  }

  @SuppressWarnings("SpellCheckingInspection")
  public void printMenu() {
    String sb = """
      --- [ ToDo Application] ---
      Please make a choice:
        (a)all items
        (f)ind a specific item
        (i)nsert a new item
        (u)update an existing item
        (d)elete an existing item
        (e)xit
    """.stripLeading();

    System.out.println(sb);
  }

  public void handleUnknownInput() {
    System.out.println("Unexpected input. Try again...");
  }

  private void printAllItems() {
    StringBuilder sb = new StringBuilder("\n---- [ToDo list] ------");

    for (ToDoItem toDoItem : repository.findAll()) {
      String completionStatus = MessageFormat.format("[{0}]", toDoItem.isCompleted() ? 'x' : '-');
      sb.append(
          MessageFormat.format("\n {0}. {1} - {2}", toDoItem.getId(), completionStatus, toDoItem.getName()));
    }

    System.out.println(sb);
  }

  private void printItemWithId() {
    Long itemId = askForId();
    ToDoItem toDoItem = repository.findById(itemId);
    if (toDoItem != null) {
      System.out.println(toDoItem);
    } else {
      System.out.println(MessageFormat.format("ToDo item with id {0} was not found.", itemId));
    }
  }

  private void createItem() {
    String toDoName = readInput("todo > ");
    ToDoItem toDoItem = ToDoItem.builder().name(toDoName).build();
    Long itemId = repository.insert(toDoItem);
    System.out.println(MessageFormat.format("Added ToDo item with id {0}", itemId));
  }

  private void updateItem() {
    Long itemId = askForId();
    ToDoItem toDoItem = repository.findById(itemId);
    if (toDoItem != null) {
      String toDoName = readInput(MessageFormat.format("[{0}] > ", toDoItem.getName()));
      if (!toDoName.isBlank()) {
        toDoItem.setName(toDoName);
      }

      while(true) {
        String completedStatus = readInput(
            MessageFormat.format("completed [{0}] > ", toDoItem.isCompleted() ? "yes" : "no"));

        if (completedStatus.equals("yes") || completedStatus.equals("no")) {
          toDoItem.setCompleted(completedStatus.equals("yes"));
          break;
        } else {
          System.out.println("Invalid input: yes/no only");
        }
      }

      System.out.println(MessageFormat.format("ToDo item with id {0} was updated.", toDoItem.getId()));
    } else {
      System.out.println(MessageFormat.format("ToDo item with id {0} was not found.", itemId));
    }
  }

  private void deleteItem() {
    Long itemId = askForId();
    ToDoItem toDoItem = repository.findById(itemId);
    if (toDoItem != null) {
      repository.delete(toDoItem);
      System.out.println(MessageFormat.format("ToDo item with id {0} was deleted", toDoItem.getId()));

    } else {
      System.out.println(MessageFormat.format("ToDo item with id {0} was not found.", itemId));
    }
  }

  private Long askForId() {
    String userInput = readInput("id > ");
    return Long.parseLong(userInput);
  }
}
