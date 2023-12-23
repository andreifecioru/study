package com.afecioru.wordz.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WordTest {

    @Test
    public void oneIncorrectLetter() {
        // Arrange
        var word = new Word("A");

        // Act
        var score = word.guess("Z");
        var actual = score.letter(0);

        // Assert
        assertThat(actual).isEqualTo(Letter.INCORRECT);
    }

    @Test
    public void oneCorrectLetter() {
        // Arrange
        var word = new Word("A");

        // Act
        var score = word.guess("A");
        var actual = score.letter(0);

        // Assert
        assertThat(actual).isEqualTo(Letter.CORRECT);
    }
}
