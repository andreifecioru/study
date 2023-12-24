package com.afecioru.mjia.streams.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public class ReadFileApp {
    public static void main(String[] args) {
        var fileName = "build.gradle";

        try (var lines = Files.lines(Paths.get(fileName), Charset.defaultCharset())) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            var errMsg = MessageFormat.format("Failed to access file {0}: {1}",
                fileName, e.getMessage()
            );
            System.err.println(errMsg);
        }
    }
}
