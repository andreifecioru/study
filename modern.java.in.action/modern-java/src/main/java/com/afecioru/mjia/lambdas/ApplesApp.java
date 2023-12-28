package com.afecioru.mjia.lambdas;

import com.afecioru.mjia.collectors.menu.Dish;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.*;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.afecioru.mjia.lambdas.Apple.Color;
import static com.afecioru.mjia.lambdas.Apples.*;

public class ApplesApp {
    public static void main(String[] args)
        throws InterruptedException, ExecutionException, TimeoutException {

        System.out.println("---- Red apples:");
        var redApples = Apples.filter(APPLES, Apples.isOfColor(Color.RED)).toList();
        Apples.prettyPrint(redApples, new Apples.ConsoleAppleFormatter());

        System.out.println("---- Green apples:");
        var greenApples = Apples.filter(APPLES, Apples.isOfColor(Color.GREEN)).toList();
        Apples.prettyPrint(greenApples, new Apples.JsonAppleFormatter());

        System.out.println("---- Apples heavier than 5:");
        var heavyApples = Apples.filter(APPLES, Apples.isHeavierThan(5)).toList();
        Apples.prettyPrint(heavyApples, new Apples.ConsoleAppleFormatter());

        System.out.println("---- Apples sorted by weight");
        var sortedApples = new ArrayList<>(APPLES);
        sortedApples.sort(Comparator.comparing(Apple::weight));
        Apples.prettyPrint(sortedApples, new Apples.ConsoleAppleFormatter());

        Thread th = new Thread(() ->
            System.out.println("---- Hello from thread")
        );
        th.start();
        th.join(1000L);

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> threadName = executorService.submit(() ->
            Thread.currentThread().getName()
        );

        System.out.println("---- Thread name: " + threadName.get(1, TimeUnit.SECONDS));

        executorService.shutdownNow();
    }
}
