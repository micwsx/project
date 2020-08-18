package com.research.java8.stream;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStream {
    public static void main(String[] args) throws URISyntaxException {

//      streams from values
        Stream<String> emptyStream = Stream.of("Java8", "Lambdas", "In", "Action");
        emptyStream.map(String::toUpperCase).forEach(System.out::println);
        Stream<String> stream = Stream.empty();
//       streams from arrays
        String[] array = {"Java", "C#"};
        Stream<String> stringStream = Arrays.stream(array);
//       streams from files
//        URI uri = BuildingStream.class.getClassLoader().getResource("").toURI();
//        File file = new File(uri);
//        String val = file + File.separator + "笔记.txt";
//        try (Stream<String> lines = Files.lines(Paths.get(val), Charset.forName("UTF-8"))) {
//            lines.forEach(System.out::println);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//      streams from functions:creating infinite streams!
        Stream.iterate(0, n -> n + 2)
                .limit(5)
                .forEach(System.out::println);
        // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55....
        Stream.iterate(new int[]{0, 1}, arr -> new int[]{arr[1], (arr[0] + arr[1])})
                .limit(20)
                .forEach(arr -> System.out.println("(" + arr[0] + "," + arr[1] + ")"));
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
        IntStream.generate(() -> 1).limit(5).forEach(System.out::println);

        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        //每次会执行getAsint()方法返回获取返回值
        IntStream.generate(fib).limit(5).forEach(System.out::println);


    }
}
