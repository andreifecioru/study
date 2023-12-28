package com.afecioru.study.ch01;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.*;


/**
 Counting duplicate characters

 Write a program that counts the occurrence of input characters in a given input string.
 */

public class Problem001 {
    public static Map<Character, Integer> solutionOne(String inputString) {
        var charsMap = new HashMap<Character, Integer>();
        for (char c : inputString.toCharArray()) {
            var count = charsMap.computeIfAbsent(c, unused -> 0);
            charsMap.put(c, ++ count);
        }

        return charsMap;
    }


    public static Map<Character, Integer> solutionTwo(String inputString) {
        return inputString.chars()
            .mapToObj(code -> (char) code)
            .collect(
                groupingBy(
                    Function.identity(),
                    collectingAndThen(counting(), Long::intValue)
                )
            );
    }
}
