package com.research.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericStreams {

    public static List<Dish> preData() {
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
        return menu;
    }

    public static void main(String[] args) {
        List<Dish> menu = preData();
        //calculate the number of calories in the menu as follows:
        int calories = menu.stream()
                .map(Dish::getCalories)//there's an insidious boxing cost
                .reduce(0, Integer::sum);
        System.out.println("totoal calories: " + calories);
        // 没有装箱（拆箱）
        calories = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(calories);
        // boxed a stream.
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();


        OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
        int max = maxCalories.orElse(1);

        System.out.println(IntStream.range(1, 100));

//      勾股定理.Math.sqrt(a * a + b * b) % 1判断是整数而不是小数
        Stream<int[]> tupleStream = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));
        tupleStream.forEach(arr -> System.out.println("(" + arr[0] + "," + arr[1] + "," + arr[2] + ")"));

        Stream<double[]> tupleStream2 = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)}))
                .filter(arr -> arr[2] % 1 == 0);
        tupleStream2.limit(5).forEach(arr -> System.out.println("(" + arr[0] + "," + arr[1] + "," + arr[2] + ")"));

        Stream<int[]> tupleStream3 = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}))
                .filter(arr -> arr[2] % 1 == 0); //这样arr[2]肯定是整数，因为在里面有强制转化
        tupleStream3.limit(5).forEach(arr -> System.out.println("(" + arr[0] + "," + arr[1] + "," + arr[2] + ")"));


    }


}
