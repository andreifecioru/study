package com.afecioru.todo.web;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.afecioru.todo.models.ToDoItem;
import com.afecioru.todo.repository.InMemoryToDoRepository;
import com.afecioru.todo.repository.ToDoRepository;
import lombok.Getter;


public class ToDoServlet extends HttpServlet {
  public static final String INDEX_PAGE = "/jsp/todo-list.jsp";

  private final ToDoRepository repository = new InMemoryToDoRepository();

  @Override
  protected void service(HttpServletRequest request,
                         HttpServletResponse response)
    throws ServletException, IOException {

    String servletPath = request.getServletPath();
    String view = processRequest(servletPath, request);
    RequestDispatcher dispatcher = request.getRequestDispatcher(view);
    dispatcher.forward(request, response);
  }

  private String processRequest(String servletPath,
                                HttpServletRequest request) {
    return switch (servletPath) {
      case "/all" -> {
        List<ToDoItem> toDoItems = repository.findAll();
        request.setAttribute("toDoItems", toDoItems);
        request.setAttribute("stats", computeStats(toDoItems));
        request.setAttribute("filter", "all");
        yield INDEX_PAGE;
      }

      case "/active" -> {
        List<ToDoItem> toDoItems = repository.findAll();
        request.setAttribute("toDoItems", filterOnStatus(toDoItems, false));
        request.setAttribute("stats", computeStats(toDoItems));
        request.setAttribute("filter", "active");
        yield INDEX_PAGE;
      }

      case "/completed" -> {
        List<ToDoItem> toDoItems = repository.findAll();
        request.setAttribute("toDoItems", filterOnStatus(toDoItems, true));
        request.setAttribute("stats", computeStats(toDoItems));
        request.setAttribute("filter", "completed");
        yield INDEX_PAGE;
      }

      case "/insert" -> {
        ToDoItem newItem = ToDoItem.builder()
            .name(request.getParameter("name"))
            .build();
        repository.insert(newItem);
        yield MessageFormat.format("/{0}", request.getParameter("filter"));
      }

      case "/update" -> {
        Long itemId = Long.parseLong(request.getParameter("id"));
        ToDoItem toDoItem = repository.findById(itemId);

        if (toDoItem != null) {
          toDoItem.setName(request.getParameter("name"));
          repository.update(toDoItem);
        }
        yield MessageFormat.format("/{0}", request.getParameter("filter"));
      }

      case "/delete" -> {
        Long itemId = Long.parseLong(request.getParameter("id"));
        ToDoItem toDoItem = repository.findById(itemId);

        if (toDoItem != null) {
          repository.delete(toDoItem);
        }
        yield MessageFormat.format("/{0}", request.getParameter("filter"));
      }

      case "/toggleStatus" -> {
        Long itemId = Long.parseLong(request.getParameter("id"));
        ToDoItem toDoItem = repository.findById(itemId);

        if (toDoItem != null) {
          toDoItem.setCompleted(!toDoItem.isCompleted());
          repository.update(toDoItem);
        }
        yield MessageFormat.format("/{0}", request.getParameter("filter"));
      }

      case "/clearCompleted" -> {
        List<ToDoItem> toDoItems = repository.findAll();

        for (ToDoItem toDoItem: toDoItems) {
          if (toDoItem.isCompleted()) {
            repository.delete(toDoItem);
          }
        }

        yield MessageFormat.format("/{0}", request.getParameter("filter"));
      }

      default -> "/all";
    };
  }

  private ToDoListStats computeStats(List<ToDoItem> toDoItems) {
    ToDoListStats stats = new ToDoListStats();

    for (ToDoItem toDoItem: toDoItems) {
      if (toDoItem.isCompleted()) {
        stats.addCompleted();
      } else {
        stats.addActive();
      }
    }

    return stats;
  }

  private List<ToDoItem> filterOnStatus(List<ToDoItem> toDoItems, boolean completed) {
    List<ToDoItem> filteredItems = new ArrayList<>();

    for(ToDoItem toDoItem: toDoItems) {
      if (toDoItem.isCompleted() == completed) {
        filteredItems.add(toDoItem);
      }
    }

    return filteredItems;
  }

  @Getter
  public static class ToDoListStats {
    private int active = 0;
    private int completed = 0;

    private void addActive() { active ++; }
    private void addCompleted() { completed ++; }

    public int getAll() { return active + completed; }
  }
}
