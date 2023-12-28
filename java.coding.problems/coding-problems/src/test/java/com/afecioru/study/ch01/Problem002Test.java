package com.afecioru.study.ch01;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;

import static com.afecioru.study.ch01.Problem002.*;


public class Problem002Test {

    @ParameterizedTest
    @MethodSource("testArguments")
    public void checkSolution(String inputString,
                              Optional<Character> expectedResult) {
        // Arrange: nothing to do

        // Act
        var actualResult = firstNonRepeatedCharacter(inputString);

        // Assert
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> testArguments() {
        return Stream.of(
            Arguments.of("", Optional.empty()),
            Arguments.of("a", Optional.of('a')),
            Arguments.of("abc", Optional.of('a')),
            Arguments.of("abca", Optional.of('b')),
            Arguments.of("abcab", Optional.of('c'))
        );
    }
}
