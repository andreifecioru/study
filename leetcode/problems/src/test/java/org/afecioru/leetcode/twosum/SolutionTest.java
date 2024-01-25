package org.afecioru.leetcode.twosum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class SolutionTest {

    @ParameterizedTest
    @MethodSource("testArguments")
    @DisplayName("Test solution 01")
    public void testSolution_01(int[] nums, int target, int[] expected) {
        // Arrange
        Solution_01 solution = new Solution_01();

        // Act
        var actual = solution.twoSum(nums, target);

        // Assert
        assertThat(actual).containsExactlyInAnyOrder(expected);
    }

    @ParameterizedTest
    @MethodSource("testArguments")
    @DisplayName("Test solution 01")
    public void testSolution_02(int[] nums, int target, int[] expected) {
        // Arrange
        Solution_02 solution = new Solution_02();

        // Act
        var actual = solution.twoSum(nums, target);

        // Assert
        assertThat(actual).containsExactlyInAnyOrder(expected);
    }

    private static Stream<Arguments> testArguments() {
        return Stream.of(
          Arguments.of(new int[] {2, 7, 11, 15}, 9, new int[] {0, 1}),
            Arguments.of(new int[] {3, 2, 4}, 6, new int[] {1, 2}),
            Arguments.of(new int[] {3, 3}, 6, new int[] {0, 1})
        );
    }
}
