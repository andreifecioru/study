package ch_02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class SysPropsTest {
    @Test
    @DisplayName("Count system props")
    void countSysProps() {
        // Arrange - nothing to do

        // Act
        var actual = SysProps.getSysPropsCount();

        // Assert
        assertThat(actual).isGreaterThan(0);
    }
}
