package com.research.java8.stream;

import com.research.java8.collecting.CaloricLevel;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class MenuDemo {


    public static void main(String[] args) {

        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Type.MEAT),
                new Dish("beef", false, 700, Type.MEAT),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("season fruit", true, 120, Type.OTHER),
                new Dish("pizza", true, 550, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH),
                new Dish("salmon", false, 450, Type.FISH)
        );



        Map<Type, Map<CaloricLevel, List<Dish>>> collect = menu.stream().collect(groupingBy(Dish::getType, groupingBy(d -> {
            if (d.getCalories() > 400)
                return CaloricLevel.DIET;
            else return CaloricLevel.FAT;
        })));

//        List<String> lowCaloriesDishesName = menu.parallelStream()
//                .filter(d -> d.getCalories() < 400)
//                .sorted((a, b) -> (a.getCalories() - b.getCalories()))
//                .map(d -> d.getName())
//                .collect(Collectors.toList());

//        List<String> lowCaloriesDishesName = menu.parallelStream()
//                .filter(d -> d.getCalories() < 400)
//                .sorted(Comparator.comparing(Dish::getCalories))
//                .map(Dish::getName)
//                .limit(2)
//                .collect(Collectors.toList());
//
//        System.out.println(lowCaloriesDishesName);
//
//        Map<Type, List<Dish>> result = menu.stream().collect(Collectors.groupingBy(Dish::getType));
//        System.out.println(result);

//      JVM同时执行mapping和filter
        List<String> names = menu.stream()
                .filter(d -> {
                    System.out.println("filter " + d.getName());
                    return d.getCalories() > 100;
                })
                .map(d -> {
                    System.out.println("mapping " + d.getName());
                    return d.getName();
                }).limit(3).collect(Collectors.toList());
        System.out.println(names);
//      intermediate operation, terminal operations
        long count = menu.stream().filter(d -> d.getCalories() > 300)
                .distinct()
                .limit(3)
                .skip(2)
                .count();
        System.out.println(count);


//      external iteration/internal iteration
//        List<Dish> veg=new ArrayList<>();
//        for (Dish dish : menu) {
//            if (dish.isVegetarian())
//                veg.add(dish);
//        }
//        List<Dish> vegetarianDish=menu.stream().filter(Dish::isVegetarian)
//                .collect(toList());

        List<String> title = Arrays.asList("Java8", "In", "Action", "Action");
        List<Integer> wordLength = title.stream().map(String::length).collect(toList());
        System.out.println(wordLength);
        List<String> cccccList = title.stream().map(s -> s.split("")).flatMap(Arrays::stream).distinct().collect(toList());
        System.out.println(cccccList);//[J, a, v, 8, I, n, A, c, t, i, o]

        List<String[]> charList = title.stream().map(s -> s.split("")).distinct().collect(toList());
        System.out.println(charList);

        String[] arrayOfWords = {"Goodbye", "World", "World"};
        Stream<String> streamOfwords = Arrays.stream(arrayOfWords);
        List<String> streamRes = streamOfwords
                .distinct()
                .collect(toList());
        System.out.println(streamRes);//[Goodbye, World]

        List<String> streamRes2 = Arrays.stream(arrayOfWords)
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct().collect(toList());
        System.out.println(streamRes2);//[G, o, d, b, y, e, W, r, l]

        //return a list of the square of each number?
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> powerDoubleList = integerList.stream().map(i -> i * i).collect(toList());
        System.out.println(powerDoubleList);

        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(3, 4);
        List<int[]> pair = list1.stream()
                .flatMap(i -> list2.stream().map(j -> new int[]{i, j}))
                .collect(toList());
        pair.stream().forEach((arr) -> System.out.println("(" + arr[0] + "," + arr[1] + ")"));

        List<int[]> dividedBy3 = list1.stream()
                .flatMap(i -> list2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(toList());
        System.out.println("dividedBy3:");
        dividedBy3.stream().forEach((arr) -> System.out.println("(" + arr[0] + "," + arr[1] + ")"));

        List<int[]> anotherDividedBy3 = pair.stream().filter(arr -> Arrays.stream(arr).sum() % 3 == 0).collect(toList());
        System.out.println("anotherDividedBy3:");
        anotherDividedBy3.stream().forEach((arr) -> System.out.println("(" + arr[0] + "," + arr[1] + ")"));
//      anyMatch
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is somewhat vegetarian friendly!!");
        }
//      allMatch
        boolean isHealthy = menu.stream().allMatch(d -> d.getCalories() < 1000);
        System.out.println(isHealthy);
//      noneMatch:(The opposite of allMatch is noneMatch),没有一个符合条件
        boolean isHealthy2 = menu.stream().noneMatch(d -> d.getCalories() >= 1000);
//      Finding an element,每次不一定返回相同结果
//        Optional<Dish> dish=menu.stream().filter(Dish::isVegetarian).findAny();
//        System.out.println(dish);
//      ifPresent()存在则执行，不存在则不执行
        menu.stream().filter(Dish::isVegetarian).findAny().ifPresent(System.out::println);
//      Finding the first element
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
//        Optional<Integer> firstSquareDivisiableByThree = someNumbers.stream()
//                .map(x -> x * x)
//                .filter(i -> i % 3 == 0)
//                .findFirst();
//        System.out.println(firstSquareDivisiableByThree);
//      initial value
//       int reslut= someNumbers.stream().reduce(0,Integer::sum);
//       System.out.println(reslut);
//        No initinal value(没有初始值)
//        Optional<Integer> sum=someNumbers.stream().reduce(Integer::sum);
//        System.out.println(sum);
//          Maximum and minimum
//        Optional<Integer> maxVal = someNumbers.stream().reduce(Integer::max);
//        System.out.println(maxVal);
//        Optional<Integer> minVal = someNumbers.stream().reduce(Integer::min);
//        System.out.println(minVal);
        long countVal = someNumbers.stream().count();
        Optional<Integer> countVal2 = someNumbers.stream().map(d -> 1).reduce(Integer::sum);
        System.out.println(countVal + "-" + countVal2);

    }


}


