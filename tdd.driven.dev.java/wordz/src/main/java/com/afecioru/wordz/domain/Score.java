package com.afecioru.wordz.domain;

public class Score {
    private final Letter[] letters = { Letter.INCORRECT };

    public Letter letter(int index) {
        return letters[index];
    }

    public void setLetterAt(Letter letter, int index) {
        letters[index] = letter;
    }
}
