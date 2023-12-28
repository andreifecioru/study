package com.afecioru.mjia.forkjoin;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class SumCalculatorApp {

    private static final ForkJoinPool threadPool = new ForkJoinPool();

    private static class SumCalculator extends RecursiveTask<Long> {
        private static final int SPLIT_THRESHOLD = 10_000;

        private final long[] numbers;
        private final int startIdx;
        private final int stopIdx;

        public SumCalculator(long[] numbers) {
            this(numbers, 0, numbers.length);
        }

        private SumCalculator(long[] numbers, int startIdx, int stopIdx) {
            this.numbers = numbers;
            this.startIdx = startIdx;
            this.stopIdx = stopIdx;
        }

        @Override
        protected Long compute() {
            int taskLength = stopIdx - startIdx;
            int middleIdx = startIdx + taskLength / 2;

            // stopping condition for forking
            if (taskLength <= SPLIT_THRESHOLD) {
                return computeSequentially();
            }

            var leftTask = new SumCalculator(numbers, startIdx, middleIdx);
            var rightTask = new SumCalculator(numbers, middleIdx, stopIdx);

            // launch the right task on a new forked thread
            rightTask.fork();

            // compute the left task on the current thread
            var leftResult = leftTask.compute();

            // wait for the right task result
            var rightResult = rightTask.join();

            return leftResult + rightResult;
        }

        private long computeSequentially() {
            long sum = 0;
            for (int i = startIdx; i < stopIdx; ++i) {
                sum += numbers[i];
            }

            return sum;
        }

        @Override
        public String toString() {
            return MessageFormat.format("([{0,number,integer}..{1,number,integer}] | start: {2,number,integer} | stop: {3,number,integer})",
                numbers[startIdx], numbers[stopIdx-1], startIdx, stopIdx
            );
        }
    }

    public static void main(String[] args) {
        var referenceSum = LongStream.rangeClosed(0, 1_000_000).sum();
        System.out.println("Reference sum: " + referenceSum);

        long[] numbers = LongStream.rangeClosed(0, 1_000_000).toArray();
        var sumCalculator = new SumCalculator(numbers);
        var result = threadPool.invoke(sumCalculator);
        System.out.println("Sum is: " + result);
    }
}
