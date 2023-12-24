package com.afecioru.mjia.streams.menu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamBasicsApp {
    public static void main(String[] args) {
        var threeHighCaloricDishes = Menu.menu.stream()
            .filter(dish -> dish.calories() > 300)
            .map(Dish::name)
            .limit(3)
            .toList();
        System.out.println("High caloric dishes: " + threeHighCaloricDishes);

        System.out.println("\n--------------------\n");


        var threeHighCaloricDishesWithLogging = Menu.menu.stream()
            .filter(dish -> {
                System.out.println("Filtering: " + dish.name());
                return dish.calories() > 300;
            })
            .map(dish -> {
                System.out.println("Mapping: " + dish.name());
                return dish.name();
            })
            .limit(3)
            .toList();
        System.out.println("High caloric dishes: " + threeHighCaloricDishesWithLogging);

        System.out.println("\n--------------------\n");

        var firstTwoMeatDishes = Menu.menu.stream()
            .filter(dish -> dish.type() == Dish.Type.MEAT)
            .limit(2)
            .toList();
        System.out.println("First two meat dishes: " + firstTwoMeatDishes);

        System.out.println("\n--------------------\n");

        var letters = Stream.of("Hello", "world")
            .map(word ->word.split(""))
            .flatMap(Arrays::stream)
            .distinct()
            .toList();
        System.out.println("Letters: " + letters);

        System.out.println("\n--------------------\n");

        var numbers = List.of(1, 2, 3, 4, 5);
        var squares = numbers.stream().map(n -> n * n).toList();
        System.out.println("Squares: " + squares);

        System.out.println("\n--------------------\n");

        var l1 = List.of(1, 2, 3);
        var l2 = List.of(4, 5);
        var pairs = l1.stream()
            .flatMap(i -> l2.stream().map(j -> List.of(i, j)))
            .filter(p -> (p.get(0) + p.get(1)) %3 == 0 )
            .toList();
        System.out.println("Pairs: " + pairs);

        System.out.println("\n--------------------\n");

        var count = Menu.menu.stream().map(unused -> 1).reduce(Integer::sum).orElse(-1);
        System.out.println("Counting the dishes in the menu: " + count);

        System.out.println("\n--------------------\n");
    }
}
