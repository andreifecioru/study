package com.afecioru.mjia.streams.numbers;

import java.text.MessageFormat;
import java.util.stream.IntStream;

public class PythagoreanNumbersApp {
    public record Triple(int a, int b, int c) {
        @Override
        public String toString() {
            return MessageFormat.format(
                "({0,number,integer}, {1,number,integer}, {2,number,integer})",
                a, b, c
            );
        }
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100)
            .boxed()
            .flatMap(a -> IntStream.rangeClosed(a, 100)
                .filter(b -> Math.sqrt(a*a + b*b) %1 == 0)
                .mapToObj(b -> {
                    var c = (int) Math.sqrt(a*a + b*b);
                    return new Triple(a, b, c);
                })
            )
            .forEach(System.out::println);
    }
}
