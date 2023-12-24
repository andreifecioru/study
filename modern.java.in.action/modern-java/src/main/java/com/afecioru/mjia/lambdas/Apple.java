package com.afecioru.mjia.lambdas;

import java.text.MessageFormat;

public record Apple(Color color, Integer weight) {
    @Override
    public String toString() {
        return MessageFormat.format(
                "Apple(color:{0}, weight:{1,number,integer})",
                color.toString(), weight
        );
    }

    public enum Color {
        RED, GREEN
    }
}
