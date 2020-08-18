package com.research.java8.testing;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Logging {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);
        numbers.stream().map(x -> x + 17).filter(x -> x % 2 == 0).limit(3).forEach(System.out::println);
        List<Integer> collect = numbers.stream().peek(i -> {
            System.out.println("from stream: " + i);
        }).map(x -> x + 17).peek(x -> {
            System.out.println("after map: " + x);
        }).filter(x -> x % 2 == 0)
                .peek(x -> {
                    System.out.println("after filter: " + x);
                }).limit(3).peek(x -> System.out.println("after limit: " + x)).collect(Collectors.toList());

        List<Integer> list = Arrays.asList(3, 5, 1, 2, 6);
        list.sort(Comparator.naturalOrder());
        System.out.println(list);
    }
}
