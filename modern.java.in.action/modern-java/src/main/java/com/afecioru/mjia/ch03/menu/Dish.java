package com.afecioru.mjia.ch03.menu;

public record Dish(String name, boolean vegetarian, int calories, Type type) {

    @Override
    public String toString() {
        return name;
    }

    public enum Type { MEAT, FISH, OTHER }
}
