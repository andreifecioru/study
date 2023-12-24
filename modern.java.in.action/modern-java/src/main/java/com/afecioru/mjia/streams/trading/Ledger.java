package com.afecioru.mjia.streams.trading;

import java.util.List;

public class Ledger {
    public static final Trader raoul = new Trader("Raoul", "Cambridge");
    public static final Trader mario = new Trader("Mario","Milan");
    public static final Trader alan = new Trader("Alan","Cambridge");
    public static final Trader brian = new Trader("Brian","Cambridge");

    public static final List<Transaction> transactions = List.of(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
    );
}
