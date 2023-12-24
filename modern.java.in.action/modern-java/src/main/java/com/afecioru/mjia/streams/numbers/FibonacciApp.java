package com.afecioru.mjia.streams.numbers;

import java.text.MessageFormat;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FibonacciApp {
    public record Pair(int a, int b) {
        @Override
        public String toString() {
            return MessageFormat.format("({0,number,integer}, {1,number,integer}",
                a, b
            );
        }
    }

    public static IntStream fibonacci() {
        return Stream.iterate(
            new Pair(0, 1),
            p -> new Pair(p.b, p.a + p.b)
        )
        .mapToInt(Pair::b);
    }

    public static IntStream fibonacci_2() {
        var nextValue = new Supplier<Pair>() {
            private Pair state = new Pair(0, 1);

            @Override
            public Pair get() {
                state = new Pair(state.b, state.a + state.b);
                return state;
            }
        };

        return Stream.generate(nextValue).mapToInt(Pair::b);
    }

    public static void main(String[] args) {
        fibonacci().limit(10).forEach(System.out::println);

        System.out.println("\n------------------\n");
        fibonacci_2().limit(10).forEach(System.out::println);

    }
}
