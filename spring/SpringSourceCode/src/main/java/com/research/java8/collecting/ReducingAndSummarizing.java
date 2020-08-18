package com.research.java8.collecting;

import com.research.java8.stream.Dish;
import com.research.java8.stream.NumericStreams;
import com.research.java8.stream.Type;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class ReducingAndSummarizing {

    public static void main(String[] args) {
        List<Dish> menu = NumericStreams.preData();
        Long howManyDishes = menu.stream().collect(Collectors.counting());
//        you can write this far more directly as:
        System.out.println(menu.stream().count());
        System.out.println(howManyDishes);

        Optional<Dish> dish = menu.stream().collect(Collectors.maxBy((d1, d2) -> (d1.getCalories() - d2.getCalories())));
        System.out.println(dish);
        Optional<Dish> dish4 = menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));
        System.out.println(dish4);
        Optional<Dish> dish2 = menu.stream().max((d1, d2) -> (d1.getCalories() - d2.getCalories()));
        System.out.println(dish2);
        Optional<Dish> dish3 = menu.stream().max(Comparator.comparingInt(Dish::getCalories));
        System.out.println(dish3);

        int totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        int totalCalories2 = menu.stream().mapToInt(d -> d.getCalories()).reduce(0, Integer::sum);
        System.out.println(totalCalories + "-" + totalCalories2);

        Double averageColories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(averageColories);

        IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);//IntSummaryStatistics{count=9, sum=4200, min=120, average=466.666667, max=800}

        String stringName = menu.stream().map(Dish::getName).collect(Collectors.joining(","));
        System.out.println(stringName);

//        Collectors.reducing()
        Integer totalCalories3 = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        System.out.println(totalCalories3);

        Optional<Dish> mostCalories = menu.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        System.out.println(mostCalories);

        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4, 5).stream();
        List<Integer> numbers = stream.reduce(new ArrayList<Integer>(),
                (List<Integer> l, Integer e) -> {
                    l.add(e);
                    return l;
                }, (List<Integer> l1, List<Integer> l2) -> {
                    l1.addAll(l2);
                    return l1;
                });
        System.out.println(numbers);

        Map<Type, List<Dish>> mapType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
        mapType.forEach((t, list) -> {
            System.out.println(t + "->" + list);
        });

//        grouping
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(Collectors.groupingBy(d -> {
            if (d.getCalories() <= 400)
                return CaloricLevel.DIET;
            else if (d.getCalories() <= 700)
                return CaloricLevel.NORMAL;
            else
                return CaloricLevel.FAT;
        }));
//      Multilevel grouping
        Map<Type, Map<CaloricLevel, List<Dish>>> dishByTypeCaloricLevel = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(d -> {
            if (d.getCalories() <= 400)
                return CaloricLevel.DIET;
            else if (d.getCalories() <= 700)
                return CaloricLevel.NORMAL;
            else
                return CaloricLevel.FAT;
        })));
        System.out.println(dishByTypeCaloricLevel);

        final Map<Type, Long> typecounts = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        System.out.println(typecounts);

        Map<Type, Optional<Dish>> typeInHighestColories = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(typeInHighestColories);

        Map<Type, Dish> mostCaloricByType = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricByType);

        Map<Type, Integer> typeInTotalCalories = menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println(typeInTotalCalories);

        Map<Type, Set<CaloricLevel>> typeInSet = menu.stream().collect(groupingBy(Dish::getType,
                mapping(d -> {
                    if (d.getCalories() <= 400)
                        return CaloricLevel.DIET;
                    else if (d.getCalories() <= 700)
                        return CaloricLevel.NORMAL;
                    else
                        return CaloricLevel.FAT;
                }, toSet())));
        System.out.println(typeInSet);

        Map<Boolean, List<Dish>> vegetarianPartition = menu.stream().collect(groupingBy(Dish::isVegetarian, toList()));
        System.out.println(vegetarianPartition);

        Map<Boolean, List<Dish>> partitionMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println(partitionMenu);
        System.out.println(partitionMenu.get(true));

        Map<Boolean, Map<Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType, toList())));
        System.out.println(vegetarianDishesByType);

        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream().collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricPartitionedByVegetarian);

        Map<Boolean, Map<Boolean, List<Dish>>> collect500Colories = menu.stream().collect(partitioningBy(Dish::isVegetarian, partitioningBy(d -> d.getCalories() > 500)));
        System.out.println(collect500Colories);

        Map<Boolean, Long> vegetarianCount = menu.stream().collect(partitioningBy(Dish::isVegetarian, counting()));
        System.out.println(vegetarianCount);

        boolean isPrime = isPrime(5);
        System.out.println(isPrime);

        System.out.println(isPrime2(9));

        System.out.println(partitionPrimes(9));

        List<Dish> customList = menu.stream().collect(new ToListCollection<Dish>());
//        List<Dish> customList = menu.stream().collect(toList());
        System.out.println(customList);

        ArrayList<Object> collect = menu.stream().collect(ArrayList::new, List::add, List::addAll);

        IntStream primeNumbers = IntStream.rangeClosed(1, 10);// Arrays.stream(new int[]{1,2,3,4,5,6,7,8,9,10});
        Map<Boolean, List<Integer>> primeMap = primeNumbers.boxed().collect(new PrimeNumberCollector());
        System.out.println(primeMap);

        Map<Boolean,ArrayList<Integer>> primeMap2=  partitionPrimeWithCustomCollector(10);
        System.out.println(primeMap2);

    }

    public static Map<Boolean,ArrayList<Integer>> partitionPrimeWithCustomCollector(int n){
        HashMap<Boolean, ArrayList<Integer>> primeMap2 = IntStream.rangeClosed(2,n).boxed().collect(() ->
                        new HashMap<Boolean, ArrayList<Integer>>() {
                            {
                                put(true, new ArrayList<Integer>());
                                put(false, new ArrayList<Integer>());
                            }
                        }
                , (map, i) -> {
                    map.get(isPrime(i)).add(i);
                }, (map1, map2) -> {
                    map1.get(true).addAll(map2.get(true));
                    map1.get(false).addAll(map2.get(false));
                });
        return primeMap2;
    }

    private static boolean isPrime(int candidate) {
        return IntStream.range(2, candidate).noneMatch(i -> candidate % i == 0);
    }

    private static boolean isPrime2(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
//        System.out.println("candidateRoot->" + candidateRoot);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> isPrime2(candidate)));
    }



}
