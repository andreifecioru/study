package com.afecioru.mjia.ch03.trading;

public record Trader(String name, String city) {
    @Override
    public String toString() {
        return "Trader: " + name + " (" + city + ")";
    }
}
