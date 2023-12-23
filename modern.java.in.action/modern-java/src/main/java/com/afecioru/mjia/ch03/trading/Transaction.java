package com.afecioru.mjia.ch03.trading;

public record Transaction(Trader trader, int year, int value) {
    @Override
    public String toString() {
        return "{" +
            trader.toString() + ", " +
            "year: " + year + ", " +
            "value: " + value +
            "}";
    }
}
