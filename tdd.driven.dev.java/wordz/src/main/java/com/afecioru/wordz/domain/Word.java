package com.afecioru.wordz.domain;

public class Word {
    private final String word;

    public Word(String word) {
        this.word = word;
    }

    public Score guess(String guess) {
        var letter = Letter.INCORRECT;

        if (word.charAt(0) == guess.charAt(0)) {
            letter = Letter.CORRECT;
        }

        var score = new Score();
        score.setLetterAt(letter, 0);

        return score;
    }
}
