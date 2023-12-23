package com.afecioru.todo.console;

import org.apache.commons.lang3.CharUtils;

import com.afecioru.todo.console.utils.CommandLineInput;
import com.afecioru.todo.console.utils.CommandLineInputHandler;


public class ToDoApp {
  private static final char DEFAULT_INPUT = '?';

  public static void main(String[] args) {
    CommandLineInputHandler cmdLineHandler = new CommandLineInputHandler();

    cmdLineHandler.readInput(">");


    while (true) {
      cmdLineHandler.printMenu();
      String input = cmdLineHandler.readInput("cmd > ");
      char command = CharUtils.toChar(input, DEFAULT_INPUT);

      CommandLineInput cmdLineInput = CommandLineInput.getCommandLineForInput(command);
      boolean shouldExit = cmdLineHandler.handleInput(cmdLineInput);

      if (shouldExit) break;
    }

    System.out.println("Bye!");
  }
}
