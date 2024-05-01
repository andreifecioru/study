package ch_02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class EnvVarsTest {
    @Test
    @DisplayName("Access the $HOME env var")
    void userHomeDir() {
        // Arrange - nothing to do

        // Act
        var actual = EnvVars.getUserHomeDir();

        // Assert
        assertThat(actual).isNotBlank();
    }
}
