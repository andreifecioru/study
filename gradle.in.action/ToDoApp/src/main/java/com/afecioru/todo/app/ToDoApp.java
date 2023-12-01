package com.afecioru.todo.app;

import com.afecioru.todo.utils.CommandLineInput;
import com.afecioru.todo.utils.CommandLineInputHandler;

import java.io.IOException;
import java.text.MessageFormat;

public class ToDoApp {
  public static void main(String[] args) {
    CommandLineInputHandler cmdLineHandler = new CommandLineInputHandler();

    while (true) {
      cmdLineHandler.printMenu();
      String input = cmdLineHandler.readInput("cmd > ");

      if (input.length() > 1) {
        cmdLineHandler.handleUnknownInput();
      } else {
        CommandLineInput cmdLineInput = CommandLineInput.getCommandLineForInput(input.charAt(0));
        boolean shouldExit = cmdLineHandler.handleInput(cmdLineInput);

        if (shouldExit) break;
      }
    }

    System.out.println("Bye!");
  }
}