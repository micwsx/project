package com.research.java8.stream.POJO;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author Michael
 * @create 11/15/2020 1:06 PM
 */
public class Apple {

    private int weigth;
    private String color;

    public Apple(int weigth, String color) {
        this.weigth = weigth;
        this.color = color;
    }

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static void main(String[] args) {

//        List<String> collect3 = Arrays.stream(new String[]{"a", "b"}).collect(toList());
//        System.out.println(collect3);

        Arrays.stream(new int[]{1,3,4});

        List<Integer> number1 = Arrays.asList(1, 2, 3);
        List<Integer> number2 = Arrays.asList(3, 4);
        List<int[]> collect2 = number1.stream()
                .flatMap(i -> number2.stream().filter(j->(i+j)%3==0)
                        .map(j -> new int[]{i, j})) // [1,3],[1,4] 使用flagMap则将结果拼接在一起。
//                .filter(array -> (array[0] + array[1]) % 3 == 0)
                .collect(toList());
        System.out.println(Arrays.toString(collect2.get(0)));
        System.out.println(Arrays.toString(collect2.get(1)));



//        String[] words=new String[]{"Hello","World"};
//        List<String> collect = Arrays.stream(words)
//                .map(word -> word.split(""))
//                .flatMap(Arrays::stream)
//                .distinct()
//                .collect(toList());
//        System.out.println(collect);
//
//        List<Integer> collect1 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5})
//                .map(i -> i * i).collect(toList());


//        Function<Integer, Integer> f = a -> a + 1;
//        Function<Integer, Integer> g = x -> x * 2;
//        Function<Integer, Integer> h = f.andThen(g);
//        Integer result = h.apply(1);
//        System.out.println(result);//4
//        h=f.compose(g);
//        result=h.apply(1);
//        System.out.println(result);//3


    }
}
