package com.research.java8.refactor;

import com.research.java8.stream.Dish;
import com.research.java8.stream.NumericStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// From imperative data processing to Streams
public class ImperativeToStreams {
    public static void main(String[] args) {
        List<Dish> menu = NumericStreams.preData();
        List<String> dishNames = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() > 300)
                dishNames.add(dish.getName());
        }
        System.out.println(dishNames);
        List<String> dishNames2 = menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName).collect(Collectors.toList());
        System.out.println(dishNames2);
        String collect = menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName).collect(Collectors.joining(","));
        System.out.println(collect);

    }
}
