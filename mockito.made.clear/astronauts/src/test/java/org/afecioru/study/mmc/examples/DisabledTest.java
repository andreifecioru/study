package org.afecioru.study.mmc.examples;

import org.junit.jupiter.api.*;

class DisabledTest {
    @Test
    @DisplayName("A disabled test")
    @Disabled("This test is disabled on purpose")
    void disabledTest() {
        System.out.println("This test is disabled.");
    }
}
