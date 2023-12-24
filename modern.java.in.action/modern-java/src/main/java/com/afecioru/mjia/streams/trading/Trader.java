package com.afecioru.mjia.streams.trading;

public record Trader(String name, String city) {
    @Override
    public String toString() {
        return "Trader: " + name + " (" + city + ")";
    }
}
