package ch_03;

import lombok.Data;

@Data
public class StringAlign {
    public enum Alignment { LEFT, RIGHT, CENTER }

    private int lineLength;
    private Alignment alignment;

    public StringAlign(int lineLength, Alignment alignment) {
        this.alignment = switch (alignment) {
            case LEFT, RIGHT, CENTER -> alignment;
            default -> throw new IllegalArgumentException("invalid alignment");
        };

        if (lineLength < 0)
            throw new IllegalArgumentException("line length cannot be negative");
    }

    public String format(String input) {
        int padLength = Math.max(lineLength - input.length(), 0) / 2;
        var padding = " ".repeat(padLength);
        return padding + input + padding;
    }
}
