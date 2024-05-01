package ch_03;

public class StringSlicing {
    static String firstHalf(String input) {
        return input.substring(0, input.length()/2);
    }

    static String secondHalf(String input) {
        return input.substring(input.length()/2);
    }
}
