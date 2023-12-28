package com.afecioru.mjia.collectors.menu;

import java.util.List;

public record Dish(String name,
                   boolean vegetarian,
                   int calories,
                   Type type,
                   List<String> tags) {

    @Override
    public String toString() {
        return name;
    }

    public enum Type { MEAT, FISH, OTHER }

    public enum CaloricLevel { DIET, NORMAL, FAT }
}
