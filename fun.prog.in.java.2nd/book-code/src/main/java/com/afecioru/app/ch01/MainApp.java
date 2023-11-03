package com.afecioru.app.ch01;

import java.util.List;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MainApp 
{
    public static void main( String[] args )
    {
        // _001_findCity();
        _002_totalThePrices();
    }

    private static void _001_findCity() {
        /** Find 'Chicago' in a list of cities */
        final List<String> cities = List.of("Bucharest", "Chicago", "Boston");

        // imperative style
        boolean found = false;
        for (String city : cities) {
            if (city.equals("Chicago")) {
                found = true;
                break;
            }
        }

        log.info("[IMPERATIVE] Found Chicago: {}", found);

        // functional style
        log.info("[FUNCTIONAL] Found Chicago: {}", cities.contains("Chicago"));
    }

    private static void _002_totalThePrices() {
        /** Total the prices greater than $20 disconted by 10%. */
        final List<Integer> prices = List.of(10, 30, 17, 20, 18, 45, 12);

        // imperative style
        double sumOfPrices = 0;
        for (int price : prices) {
            if (price > 20) {
                sumOfPrices += price * 0.9;
            }
        }
        log.info("[IMPERATIVE] Sum of prices: {}", sumOfPrices);

        // functional style
        double sumOfPricesFun = prices.stream()
            .filter(price -> price > 20)
            .mapToDouble(price -> price * 0.9)
            .sum();
        log.info("[FUNCTIONAL] Sum of prices: {}", sumOfPricesFun);
    }
}
