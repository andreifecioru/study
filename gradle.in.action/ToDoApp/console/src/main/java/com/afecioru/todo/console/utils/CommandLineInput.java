package com.afecioru.todo.console.utils;

import java.util.HashMap;
import java.util.Map;


public enum CommandLineInput {
    FIND_ALL('a'),
    FIND_BY_ID('f'),
    INSERT('i'),
    UPDATE('u'),
    DELETE('d'),
    EXIT('e');

    private final static Map<Character, CommandLineInput> INPUTS;

    static {
        INPUTS = new HashMap<>();
        for (CommandLineInput input: values()) {
            INPUTS.put(input.shortCmd, input);
        }
    }

    private final char shortCmd;

    private CommandLineInput(char shortCmd) {
        this.shortCmd = shortCmd;
    }

    public static CommandLineInput getCommandLineForInput(char input) {
        return INPUTS.get(input);
    }
}
