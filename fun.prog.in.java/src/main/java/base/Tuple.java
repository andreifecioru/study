package base;

public class Tuple<T, U> {
    public final T _1;
    public final U _2;
    
    public Tuple(T _1, U _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString() {
        return "Tuple(" + _1 + ", " + _2 + ")";
    }
}