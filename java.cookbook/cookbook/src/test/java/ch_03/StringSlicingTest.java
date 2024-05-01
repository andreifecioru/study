package ch_03;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;


public class StringSlicingTest {
    @ParameterizedTest
    @MethodSource("firstHalfOfStringArguments")
    @DisplayName("Taking 1st half of input string")
    void firstHalfOfString(String inputString, String expectedResult) {
        // Arrange - do nothing

        // Act
        var actual = StringSlicing.firstHalf(inputString);

        // Assert
        assertThat(actual).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("secondHalfOfStringArguments")
    @DisplayName("Taking 2nd half of input string")
    void secondHalfOfString(String inputString, String expectedResult) {
        // Arrange - do nothing

        // Act
        var actual = StringSlicing.secondHalf(inputString);

        // Assert
        assertThat(actual).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> firstHalfOfStringArguments() {
        return Stream.of(
            Arguments.of("", ""),
            Arguments.of("0123456789", "01234")
        );
    }

    private static Stream<Arguments> secondHalfOfStringArguments() {
        return Stream.of(
            Arguments.of("", ""),
            Arguments.of("0123456789", "56789")
        );
    }
}
