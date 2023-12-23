package com.afecioru.todo.repository;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitWithParamsTest {

    @ParameterizedTest
    @MethodSource("testInputs")
    void testMin(int a, int b, int result) {
        assertEquals(Math.min(a, b), result);
    }

    private static Stream<Arguments> testInputs() {
        return Stream.of(
            Arguments.of(1, 2, 1),
            Arguments.of(5, 2, 2)
        );
    }
}
