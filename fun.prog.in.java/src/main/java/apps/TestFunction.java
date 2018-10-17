package apps;

import base.*;

public class TestFunction {
    public static void main(String[] args) {
        Function<String, Integer> length = in -> in.length();
        Function<Integer, Integer> triple = x -> x * 3;

        Function<String, Integer> compose2 = Functions.<String, Integer, Integer>compose_curried().apply(triple).apply(length);

        System.out.println("Andrei length tripled is: " + Functions.compose(triple, length).apply("Andrei"));
        System.out.println("Andrei length tripled (2) is: " + compose2.apply("Andrei"));
    }
}