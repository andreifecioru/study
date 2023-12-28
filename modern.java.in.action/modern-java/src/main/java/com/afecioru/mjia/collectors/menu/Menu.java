package com.afecioru.mjia.collectors.menu;

import java.util.List;

public class Menu {
    public static List<Dish> menu = List.of(
        new Dish("pork", false, 800, Dish.Type.MEAT, List.of("greasy", "salty")),
        new Dish("beef", false, 700, Dish.Type.MEAT, List.of("salty", "roasted")),
        new Dish("chicken", false, 400, Dish.Type.MEAT, List.of("fried", "crispy")),
        new Dish("french fries", true, 530, Dish.Type.OTHER, List.of("greasy", "fried")),
        new Dish("rice", true, 350, Dish.Type.OTHER, List.of("light", "natural")),
        new Dish("season fruit", true, 120, Dish.Type.OTHER, List.of("fresh", "natural")),
        new Dish("pizza", true, 550, Dish.Type.OTHER, List.of("tasty", "salty")),
        new Dish("prawns", false, 300, Dish.Type.FISH, List.of("tasty", "roasted")),
        new Dish("salmon", false, 450, Dish.Type.FISH, List.of("delicious", "fresh"))
    );
}
