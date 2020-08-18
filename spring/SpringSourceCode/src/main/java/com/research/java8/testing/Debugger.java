package com.research.java8.testing;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Debugger {
    public static void main(String[] args) {
//        List<Point> points = Arrays.asList(new Point(12, 2), null);
        //points.stream().map(p->p.getX()).forEach(System.out::println);
//        points.stream().map(Point::getX).forEach(System.out::println);

        List<Integer> list = Arrays.asList(1, 2, 3);
        list.stream().map(Debugger::divideByZero).forEach(System.out::println);

    }

    public static int divideByZero(int n) {
        return n / 0;
    }
}
