package com.afecioru.mjia.collectors.primes;

import com.afecioru.mjia.utils.Profiling;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class FasterPrimeNumbersApp {

    private static class PrimePartitioner
        implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

        public static PrimePartitioner byPrimes() {
            return new PrimePartitioner();
        }

        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (acc, n) -> {
                var primesSoFar = acc.get(true);
                acc.computeIfAbsent(isPrime(n, primesSoFar), unused -> new ArrayList<>()).add(n);
            };
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (m1, m2) -> {
                throw new UnsupportedOperationException("Non-parallel implementation.");
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of(Characteristics.IDENTITY_FINISH);
        }

        private boolean isPrime(int n, List<Integer> acc) {
            if (acc == null) return true;

            return acc.stream()
                .takeWhile(i -> i < Math.sqrt(n))
                .noneMatch(i -> n % i == 0);
        }
    }

    private static final Predicate<Integer> isPrime = candidate ->
        IntStream.rangeClosed(2, candidate / 2).noneMatch(n -> candidate % n == 0);

    private static final int MAX_NUMBER = 100_000;
    private static final int ITERATION_COUNT = 10;

    public static void main(String[] args) {

        var nonOptimizedResult = Profiling.profile(ITERATION_COUNT, () ->
            IntStream.rangeClosed(2, MAX_NUMBER).boxed()
                .collect(partitioningBy(isPrime))
        );
        System.out.println("[NON-OPTIMIZED] Avg execution duration: " + nonOptimizedResult + "ms");

        var optimizedResult = Profiling.profile(ITERATION_COUNT, () ->
            IntStream.rangeClosed(2, 100).boxed()
                .collect(PrimePartitioner.byPrimes())
        );
        System.out.println("[OPTIMIZED] Avg execution duration: " + optimizedResult + "ms");
    }
}
