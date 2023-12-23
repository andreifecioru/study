package com.afecioru.mjia.ch03.trading;

import java.util.Comparator;

public class TradingApp {
    public static void main(String[] args) {
        System.out.println("/n-------------------/n");

        // All transactions from 2011 sorted by value ASC.
        var q1Result = Ledger.transactions.stream()
            .filter(t -> t.year() == 2011)
            .sorted(Comparator.comparing(Transaction::value))
            .toList();
        System.out.println("[1] Result: " + q1Result);

        System.out.println("/n-------------------/n");

        // Unique cities where traders work
        var q2Result = Ledger.transactions.stream()
            .map(Transaction::trader)
            .map(Trader::city)
            .distinct()
            .toList();
        System.out.println("[2] Result: " + q2Result);

        System.out.println("/n-------------------/n");

        // All traders from Cambridge sorted by name ASC.
        var q3Result = Ledger.transactions.stream()
            .map(Transaction::trader)
            .filter(trader -> "Cambridge".equals(trader.city()))
            .distinct()
            .sorted(Comparator.comparing(Trader::name))
            .toList();
        System.out.println("[3] Result: " + q3Result);

        System.out.println("/n-------------------/n");

        // All trader names sorted alphabetically
        var q4Result = Ledger.transactions.stream()
            .map(Transaction::trader)
            .map(Trader::name)
            .distinct()
            .sorted()
            .toList();
        System.out.println("[4] Result: " + q4Result);

        System.out.println("/n-------------------/n");

        // Any traders in Milan?
        var q5Result = Ledger.transactions.stream()
            .map(Transaction::trader)
            .anyMatch(trader -> "Milan".equals(trader.city()));
        System.out.println("[5] Result: " + q5Result);

        System.out.println("/n-------------------/n");

        // All the transaction values originating from Cambridge
        var q6Result = Ledger.transactions.stream()
            .filter(transaction -> "Cambridge".equals(transaction.trader().city()))
            .map(Transaction::value)
            .toList();
        System.out.println("[6] Result: " + q6Result);

        System.out.println("/n-------------------/n");

        // The value of the highest transaction
        var q7Result = Ledger.transactions.stream()
            .max(Comparator.comparing(Transaction::value))
            .map(Transaction::value)
            .orElse(0);
        System.out.println("[7] Result: " + q7Result);

        System.out.println("/n-------------------/n");

        // The value of the smallest transaction
        var q8Result = Ledger.transactions.stream()
            .min(Comparator.comparing(Transaction::value))
            .map(Transaction::value)
            .orElse(0);
        System.out.println("[8] Result: " + q8Result);

        System.out.println("/n-------------------/n");
    }
}
