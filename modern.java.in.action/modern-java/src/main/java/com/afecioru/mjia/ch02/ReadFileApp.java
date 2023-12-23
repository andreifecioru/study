package com.afecioru.mjia.ch02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.stream.IntStream;

@SuppressWarnings("SameParameterValue")
public class ReadFileApp {

    @FunctionalInterface
    private interface BufferedReaderProcessor {
        String process(BufferedReader br);
    }

    private static String processFile(String fileName,
                                      BufferedReaderProcessor processor) {
        try (var fr = new FileReader(fileName);
             var br = new BufferedReader(fr)) {
            return processor.process(br);
        } catch (Exception e) {
            var errMsg = MessageFormat.format(
                "Failed to process file {0}: {1}",
                fileName, e.getMessage()
            );
            System.err.println(errMsg);
            return errMsg;
        }
    }

    private static BufferedReaderProcessor readMultipleLines(int lineCount) {
        return (bufferedReader) -> {
            var sb = new StringBuilder();
            IntStream.range(0, lineCount)
                .forEach(unused -> {
                    try {
                        sb.append(bufferedReader.readLine()).append('\n');
                    } catch (IOException e) {
                        var errMsg = MessageFormat.format("Failed to read file {0}", e.getMessage());
                        System.err.println(errMsg);
                    }
                });
            return sb.toString();
        };
    }


    public static void main(String[] args) {
        var content = processFile("build.gradle", readMultipleLines(10));
        System.out.println(content);
    }
}
