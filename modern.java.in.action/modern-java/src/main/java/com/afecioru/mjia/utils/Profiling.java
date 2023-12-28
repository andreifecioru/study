package com.afecioru.mjia.utils;

import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public class Profiling {
    public static <T> double profile(int iterationCount, Callable<T> block) {
        return LongStream.rangeClosed(1, iterationCount)
            .map(unused -> {
                var start = System.nanoTime();
                try {
                    block.call();
                } catch (Exception e) {
                    var errMsg = MessageFormat.format("Error during callable invocation. {0}", e.getMessage());
                    System.err.println(errMsg);
                }

                return (System.nanoTime() - start) / 1_000_000;
            })
            .average()
            .orElse(0.0);
    }
}
