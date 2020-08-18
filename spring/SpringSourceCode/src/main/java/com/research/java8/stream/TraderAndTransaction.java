package com.research.java8.stream;

import java.util.*;
import java.util.stream.Collectors;

public class TraderAndTransaction {

    public static List<Transaction> prepareData() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        return transactions;
    }

    public static void main(String[] args) {
        List<Transaction> transactions = prepareData();
//      1. Find all transaction in the year 2011 and sort them by value (small to high).
        List<Transaction> transInYear2011 = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(transInYear2011);
//      2. What are the unique cities where the traders work?
        List<String> uniqueCities = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueCities);
        Set<String> uniqueCities2 = transactions.stream()
                .map(t -> t.getTrader().getCity())
                .collect(Collectors.toSet());
        System.out.println(uniqueCities2);
//      3. Find all traders from Cambridge and sort them by name.
        List<Trader> tradersInCambridge = transactions.stream()
                .filter(t -> t.getTrader().getCity() == "Cambridge")
                .map(t -> t.getTrader())
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(tradersInCambridge);
//       4. Return a string of all traders' names sorted alphabetically.
        String allNamesInALine = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted(String::compareTo)
                .reduce("", (a, b) -> a + b);
        System.out.println(allNamesInALine);
        String traderStr = transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining());
        System.out.println(traderStr);
//      5. Are any traders based in Milan?
        boolean anyTradersInMilan = transactions.stream().anyMatch(t -> t.getTrader().getCity() == "Milan");
        System.out.println("Are any traders based in Milan? -" + anyTradersInMilan);
//      6. Print all transactions values from the traders living in Cambridge
        transactions.stream()
                .filter(t -> t.getTrader().getCity() == "Cambridge")
                .map(t -> t.getValue()).forEach(System.out::println);
//       7. What's the highest value of all the transactions?
        Optional<Integer> highestValue = transactions.stream()
                .map(t -> t.getValue())
                .reduce(Integer::max);
        System.out.println(highestValue);

//       8. Find the transaction with the smallest value.
        Optional<Integer> smallestValue = transactions.stream()
                .map(t -> t.getValue())
                .reduce(Integer::min);
        System.out.println(smallestValue);

        Optional<Transaction> smallestTrans2 = transactions.stream().min(Comparator.comparing(t -> t.getValue()));
        System.out.println(smallestTrans2);

        Optional<Transaction> smallestTrans3 = transactions.stream().reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
        System.out.println(smallestTrans3);
    }
}
