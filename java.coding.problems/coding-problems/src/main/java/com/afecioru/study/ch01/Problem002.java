package com.afecioru.study.ch01;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


/**
Finding the 1st non-repeated character.

Write a program that returns the 1st non-repeated character from a given string.
*/

public class Problem002 {

    private record CharStats(int count, int pos) {};

    private static Collector<Character, ?, Map<Character, CharStats>> toCharStats(String inputString) {
        return Collectors.toMap(
            Function.identity(),
            c -> new CharStats(1, inputString.indexOf(c)),
            (s1, s2) -> new CharStats(s1.count + s2.count, Math.min(s1.pos, s2.pos))
        );
    }

    public static Optional<Character> firstNonRepeatedCharacter(String inputString) {
        return inputString.chars()
            .mapToObj(code -> (char) code)
            .collect(toCharStats(inputString))
            .entrySet().stream()
            .filter(e -> e.getValue().count == 1)  // non-repeating character
            .collect(minBy(Comparator.comparingInt(e -> e.getValue().pos)))
            .map(Map.Entry::getKey); // find 1st
    }
}
