package ch_02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class CheckSysDepsTest {
    @Test
    @DisplayName("Is Swing available?")
    void checkSwingAvailability() {
        // Arrange - nothing to do

        // Act
        var actual = CheckSysDeps.isSwingAvailable();

        // Assert
        assertThat(actual).isFalse();
    }
}
