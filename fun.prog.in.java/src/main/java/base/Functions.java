package base;

public final class Functions {
    public static <T, U, R> Function<T, R> compose(Function<U, R> f1, Function<T, U> f2) {
        return arg -> f1.apply(f2.apply(arg));
    }

    public static <T, U, R> Function<Function<U, R>, Function<Function<T, U>, Function<T, R>>> compose_curried() {
        return (Function<U, R> f1) -> (Function<T, U> f2) -> (T arg) -> f1.apply(f2.apply(arg)); 
    }

    private Functions() {
        throw new IllegalArgumentException("Utility class");
    }
}