package apps;


import base.*;

public class TestTuple {
    public static void main(String[] args) {
        Tuple<Integer, String> t = new Tuple<>(1, "Andrei");
        System.out.println(t);
    }
}