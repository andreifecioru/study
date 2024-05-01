package ch_03;


import java.util.function.Function;

public class StringTokenization {
    record Person(String firstName, String lastName, Integer age, Double salary) {}

    static Person parsePerson(String rawRecord, String delimiter) {
        var tokens = rawRecord.split(delimiter);

        var firstName = extractStringAt(tokens, 0);
        var lastName = extractStringAt(tokens, 1);
        var age = extractIntAt(tokens, 2);
        var salary = extractDoubleAt(tokens, 3);

        return new Person(firstName, lastName, age, salary);
    }



    static private <T> T extractAt(String[] tokens, int idx, Function<String, T> extractor) {
        T result = null;
        var token = tokens.length > idx ? tokens[idx] : null;

        if (token != null) {
            try {
                result = extractor.apply(token);
            } catch (Exception ex) {
                // do nothing
            }
        }

        return result;
    }

    static private String extractStringAt(String[] tokens, int idx) {
        return extractAt(tokens, idx, Function.identity());
    }

    static private Integer extractIntAt(String[] tokens, int idx) {
        return extractAt(tokens, idx, Integer::parseInt);
    }

    static private Double extractDoubleAt(String[] tokens, int idx) {
        return extractAt(tokens, idx, Double::parseDouble);
    }
}

