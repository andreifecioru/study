package com.afecioru.todo.app;

import org.apache.commons.lang3.CharUtils;

import com.afecioru.todo.utils.CommandLineInput;
import com.afecioru.todo.utils.CommandLineInputHandler;


public class ToDoApp {
  private static final char DEFAULT_INPUT = '?';

  public static void main(String[] args) {
    CommandLineInputHandler cmdLineHandler = new CommandLineInputHandler();

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