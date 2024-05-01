package ch_03;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.*;
import static ch_03.StringTokenization.Person;


public class StringTokenizationTest {

    @ParameterizedTest
    @MethodSource("personParserArguments")
    @DisplayName("Parsing persons from tokenized string")
    void firstHalfOfString(String inputString, Person expected) {
        // Arrange - do nothing

        // Act
        var actual = StringTokenization.parsePerson(inputString, "\\|");

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
    private static Stream<Arguments> personParserArguments() {
        return Stream.of(
            Arguments.of("John|Smith|40|1200.5", new Person("John", "Smith", 40, 1200.5)),
            Arguments.of("John|Smith|40", new Person("John", "Smith", 40, null)),
            Arguments.of("John|Smith", new Person("John", "Smith", null, null)),
            Arguments.of("John", new Person("John", null, null, null)),
            Arguments.of("John|Smith||1200.5", new Person("John", "Smith", null, 1200.5))
        );
    }

}
