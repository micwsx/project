package com.research.java8.collecting;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * T: stream流中的数据 类型
 * A ：accumulate部分结果数据类型
 * R：返回类型
 *
 * 处理对象是：整形Integer集合
 * 返回类型：Map
 */
public class PrimeNumberCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {
            {
                put(true, new ArrayList<>());
                put(false, new ArrayList<>());
            }
        };
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (booleanListMap, integer) -> {
            int sqrtRoot = (int) Math.sqrt(integer);
            boolean isPrime = IntStream.rangeClosed(2, sqrtRoot).noneMatch(i -> integer % i == 0);
            booleanListMap.get(isPrime).add(integer);
        };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (booleanListMap, booleanListMap2) -> {
            // 合并两个map
            booleanListMap.get(true).addAll(booleanListMap2.get(true));
            booleanListMap.get(false).addAll(booleanListMap2.get(false));
            return  booleanListMap;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
    }
}
