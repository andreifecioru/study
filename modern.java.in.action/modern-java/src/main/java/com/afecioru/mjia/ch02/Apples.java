package com.afecioru.mjia.ch02;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.afecioru.mjia.ch02.Apple.Color;

public class Apples {
    public static final List<Apple> APPLES = List.of(
        new Apple(Apple.Color.RED, 3),
        new Apple(Apple.Color.RED, 6),
        new Apple(Apple.Color.GREEN, 4),
        new Apple(Apple.Color.GREEN, 9)
    );

    public static Predicate<Apple> isOfColor(Color color) {
        return (Apple apple) -> color.equals(apple.color());
    }

    public static Predicate<Apple> isHeavierThan(int weight) {
        return (Apple apple) -> apple.weight() >= weight;
    }

    public static Stream<Apple> filter(Collection<Apple> apples, Predicate<Apple> predicate) {
        return apples.stream().filter(predicate);
    }

    public static void prettyPrint(List<Apple> apples, AppleFormatter formatter) {
        apples.forEach(apple -> System.out.println(formatter.format(apple)));
    }

    public interface AppleFormatter {
        String format(Apple apple);
    }

    public static class ConsoleAppleFormatter implements AppleFormatter {
        @Override
        public String format(Apple apple) {
            return apple.toString();
        }
    }

    public static class JsonAppleFormatter implements AppleFormatter {
        @Override
        public String format(Apple apple) {
            return MessageFormat.format(
                "'{'color: {0}, weight: {1,number,integer}}",
                apple.color(), apple.weight()
            );
        }
    }
}
