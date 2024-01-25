package org.afecioru.study.mmc.examples;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LifecycleTest {
    LifecycleTest() {
        System.out.println("\n  ++ Constructor");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("  >> Before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("  << After each");
    }

    @BeforeAll
    void beforeAll() {
        System.out.println("---- Before all");
    }

    @AfterAll
    void afterAll() {
        System.out.println("---- After all");
    }

    @Test
    @DisplayName("First test")
    void firstTest() {
        System.out.println("      First test");
    }

    @Test
    @DisplayName("Second test")
    void secondTest() {
        System.out.println("      Second test");
    }

    @Test
    @DisplayName("Third test")
    void thirdTest() {
        System.out.println("      Third test");
    }
}
