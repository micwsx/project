package com.research.java8.testing;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Testinglambda {

    public static void main(String[] args) {
//        Point point1 = new Point(5, 5);
//        Point point2 = point1.moveRightBy(10);
//        int compare = Point.compareByXandThenY.compare(point1, point2);//-1
//        System.out.println(compare);

//        List<Point> list = new ArrayList<Point>() {
//            {
//                add(new Point(5, 5));
//                add(new Point(10, 10));
//                add(new Point(20, 20));
//                add(new Point(30, 30));
//            }
//        };

        List<Point> list= Arrays.asList(new Point(5,5),new Point(15,15));

        List<Point> points = moveAllRightBy(list, 10);
        System.out.println(points);

    }

    public static List<Point> moveAllRightBy(List<Point> list, int x) {
        return list.stream().collect(Collectors.mapping(p -> p.moveRightBy(x), Collectors.toList()));
    }

}

class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point moveRightBy(int x) {
        return new Point(this.x + x, this.y);
    }

    public final static Comparator<Point> compareByXandThenY = Comparator.comparing(Point::getX).thenComparing(Point::getY);

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
