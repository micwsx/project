package com.research.java8.collecting;

import com.research.java8.stream.TraderAndTransaction;
import com.research.java8.stream.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Prelude {


    public static void main(String[] args) {

        List<Transaction> transactions = TraderAndTransaction.prepareData();
        // grouping transactions by year in imperative style
        Map<Integer, List<Transaction>> groupMap = new HashMap<>();
        for (Transaction transaction : transactions) {
            Integer year = transaction.getYear();
            List<Transaction> transList = groupMap.get(year);
            if (transList == null) {
                transList = new ArrayList<>();
                groupMap.put(year, transList);
            }
            transList.add(transaction);
        }
        groupMap.forEach((year, list) -> {
            System.out.println("year: " + year);
            System.out.println(">>>" + list);
        });

        Map<Integer, List<Transaction>> transactionByYear = transactions.stream().collect(Collectors.groupingBy(Transaction::getYear));
        transactionByYear.forEach((year, list) -> {
            System.out.println("year: " + year);
            System.out.println(">>>" + list);
        });



    }
}
