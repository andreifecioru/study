package com.afecioru.study.ch01;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;

import static com.afecioru.study.ch01.Problem001.*;


public class Problem001Test {
    @ParameterizedTest
    @MethodSource("testArguments")
    public void checkSolutionOne(String inputString,
                                 Map<Character, Integer> expectedResult) {
        // Arrange: nothing to do

        // Act
        var charCounts = solutionOne(inputString);

        // Assert
        assertThat(charCounts).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("testArguments")
    public void checkSolutionTwo(String inputString,
                                 Map<Character, Integer> expectedCount) {
        // Arrange: nothing to do

        // Act
        var charCounts = solutionTwo(inputString);

        // Assert
        assertThat(charCounts).isEqualTo(expectedCount);
    }

    private static Stream<Arguments> testArguments() {
        return Stream.of(
            Arguments.of("", Collections.emptyMap()),
            Arguments.of("a", Map.of('a', 1)),
            Arguments.of("aa", Map.of('a', 2)),
            Arguments.of("aaba", Map.of('a', 3, 'b', 1)),
            Arguments.of("aabba", Map.of('a', 3, 'b', 2))
        );
    }
}
