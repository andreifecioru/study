package com.afecioru.mjia.collectors.menu;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static com.afecioru.mjia.collectors.menu.Dish.CaloricLevel.*;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class CollectorsBasicApp {
    @SuppressWarnings("SimplifyStreamApiCallChains")
    public static void main(String[] args) {
        // Counting elements in stream
        var dishCount = Menu.menu.stream().collect(counting());
        System.out.println("Dish count: " + dishCount);

        var dishCount_1 = Menu.menu.stream().count();
        System.out.println("Dish count (short): " + dishCount_1);
        System.out.println("\n--------------------\n");

        // Finding max/min element (by a criterion)
        Menu.menu.stream()
            .collect(minBy(comparingInt(Dish::calories)))
            .ifPresent(dish -> {
                var message = MessageFormat.format("Dish with min calories {0} ({1,number, integer} cals)",
                    dish.name(), dish.calories()
                );
                System.out.println(message);
            });
        System.out.println("\n--------------------\n");

        // Summarization: find the average of elements (by first mapping elements to numbers)
        var avgCalories = Menu.menu.stream().collect(averagingInt(Dish::calories)).intValue();
        System.out.println("Average calories per dish: " + avgCalories);

        var stats = Menu.menu.stream().collect(summarizingInt(Dish::calories));
        System.out.println("Dish calories stats: " + stats);
        System.out.println("\n--------------------\n");

        // Joining string representation of elements
        var dishNames = Menu.menu.stream().map(Dish::name).collect(joining(", "));
        System.out.println("Available dishes: " + dishNames);

        var dishNames_1 = Menu.menu.stream()
            .collect(reducing("", Dish::name,
                (n1, n2) -> (n1.isEmpty()) ? n2 : n1 + ", " + n2));
        System.out.println("Available dishes (reducing): " + dishNames_1);
        System.out.println("\n--------------------\n");

        // Grouping elements by criterion
        var dishesByType = Menu.menu.stream()
            .collect(groupingBy(Dish::type));
        System.out.println("Dishes grouped by type: " + dishesByType);
        System.out.println("\n--------------------\n");

        var dishesByCaloricLevel = Menu.menu.stream()
            .collect(groupingBy(dish -> {
                if (dish.calories() <= 400) return DIET;
                else if (dish.calories() <= 700) return NORMAL;
                else return FAT;
            }));
        System.out.println("Dishes grouped by caloric level: " + dishesByCaloricLevel);
        System.out.println("\n--------------------\n");

        // Additional filtering applied to the element groups
        var dishesByType_1 = Menu.menu.stream()
            .collect(groupingBy(
                Dish::type,
                filtering(dish -> dish.calories() > 500, toList())
            ));
        System.out.println("Caloric dishes grouped by type: " + dishesByType_1);
        System.out.println("\n--------------------\n");

        // Additional mapping applied to the element groups
        var dishNamesByType = Menu.menu.stream()
            .collect(groupingBy(
                Dish::type,
                mapping(dish -> dish.name().toUpperCase(), toList())
            ));
        System.out.println("Dish names grouped by type: " + dishNamesByType);
        System.out.println("\n--------------------\n");

        var dishTagsByDishType = Menu.menu.stream()
            .collect(groupingBy(
                Dish::type,
                flatMapping(dish -> dish.tags().stream(), toSet())
            ));
        System.out.println("Dish tags grouped by dish type: " + dishTagsByDishType);
        System.out.println("\n--------------------\n");

        // Count the number of dishes of each type
        var dishCountByType = Menu.menu.stream()
            .collect(groupingBy(
               Dish::type,
               counting()
            ));
        System.out.println("Dish counts by type:" + dishCountByType);
        System.out.println("\n--------------------\n");

        // Multi-level grouping
        var dishesByTypeAndCaloricLoad = Menu.menu.stream()
            .collect(
                groupingBy(Dish::type,
                    groupingBy(dish -> {
                        if (dish.calories() <= 400) return DIET;
                        else if (dish.calories() <= 700) return NORMAL;
                        else return FAT;
                    })
                )
            );
        System.out.println("Dishes grouped by type and caloric load:" + dishesByTypeAndCaloricLoad);
        System.out.println("\n--------------------\n");

        // Highest caloric dish for each type of dish
        var query1 = Menu.menu.stream()
            .collect(groupingBy(
                Dish::type,
                collectingAndThen(
                    maxBy(comparingInt(Dish::calories)),
                    Optional::get
                )
            ));
        System.out.println("Highest caloric dish for each type of dish:" + query1);
        System.out.println("\n--------------------\n");

        // ... simplified version of the above
        var query2 = Menu.menu.stream()
            .collect(toMap(
                Dish::type,                                             // key extractor
                Function.identity(),                                    // value extractor
                BinaryOperator.maxBy(comparingInt(Dish::calories))      // value aggregator
            ));
        System.out.println("Highest caloric dish for each type of dish (2):" + query2);
        System.out.println("\n--------------------\n");

        // Which caloric levels are available for each type of dish
        var query3 = Menu.menu.stream()
            .collect(groupingBy(
                Dish::type,
                mapping(dish -> {
                    if (dish.calories() <= 400) return DIET;
                    else if (dish.calories() <= 700) return NORMAL;
                    else return FAT;
                }, toSet())
            ));
        System.out.println("Caloric levels available per dish type:" + query3);
        System.out.println("\n--------------------\n");

        // Partitioning: vegetarian or not and grouped by dish type
        var query4 = Menu.menu.stream()
            .collect(partitioningBy(
                Dish::vegetarian,
                groupingBy(Dish::type)
            ));
        System.out.println("Vegetarian dishes:" + query4.get(true));
        System.out.println("Meat-based dishes:" + query4.get(false));
        System.out.println("\n--------------------\n");

        // Count the vegetarian vs. meat-based dishes
        var query5 = Menu.menu.stream()
            .collect(partitioningBy(
                Dish::vegetarian,
                counting()
            ));
        System.out.println("Vegetarian dishes:" + query5.get(true));
        System.out.println("Meat-based dishes:" + query5.get(false));
        System.out.println("\n--------------------\n");
    }
}
