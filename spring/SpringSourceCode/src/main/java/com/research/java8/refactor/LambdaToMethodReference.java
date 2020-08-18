package com.research.java8.refactor;

import com.research.java8.collecting.CaloricLevel;
import com.research.java8.stream.Dish;
import com.research.java8.stream.MenuDemo;
import com.research.java8.stream.NumericStreams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LambdaToMethodReference {

    public static void main(String[] args) {
        List<Dish> menu = NumericStreams.preData();
        Map<CaloricLevel, List<Dish>> collect = menu.stream().collect(Collectors.groupingBy(d -> {
            if (d.getCalories() <= 400)
                return CaloricLevel.DIET;
            if (d.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }));
        System.out.println(collect);

        Map<CaloricLevel, List<Dish>> collect1 = menu.stream().collect(Collectors.groupingBy(Dish::getCaloricLevel));
        System.out.println(collect1);

        Integer total = menu.stream().map(Dish::getCalories).reduce(0, (c1, c2) -> c1 + c2);
        Integer total2 = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);

        Integer total3 = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println("total:" + total + "-total2:" + total2 + "-total3:" + total3);
    }


}
