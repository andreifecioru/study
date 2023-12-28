package com.afecioru.mjia.collectors.primes;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class PrimeNumbersApp {
    private static final Predicate<Integer> isPrime = candidate ->
        IntStream.rangeClosed(2, candidate / 2).noneMatch(n -> candidate % n == 0);

    public static void main(String[] args) {
        // Partition numbers as primes vs. non-primes
        var query1 = IntStream.rangeClosed(2, 100)
            .boxed()
            .collect(partitioningBy(isPrime));
        System.out.println("Primes: " + query1.get(true).stream().limit(10).toList());
        System.out.println("Non-primes: " + query1.get(false).stream().limit(10).toList());

        var sum = LongStream.rangeClosed(0, 1_000_000)
            .parallel()
            .sum();
        System.out.println("Sum is: " + sum);

        var sum1 = Stream.iterate(1L, i -> ++i )
            .limit(1_000_000)
            .parallel()
            .mapToLong(Long::longValue)
            .sum();

        System.out.println("Sum is: " + sum1);
    }
}
