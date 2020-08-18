package com.research.java8.parallel;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStream {

    public static void main(String[] args) {

        System.out.println("ForkJoin sum done in: " + measureSumPerf(
                ForkJoinSumCalculator::forkJoinSum, 10_000_000) + " msecs" );

//        System.out.println("SideEffectParallelSum sum done in: " +
//                measureSumPerf(ParallelStream::sideEffectParallelSum, 10_000_000) + " msecs");//1ms
//
//        System.out.println("SideEffectSum sum done in: " +
//                measureSumPerf(ParallelStream::sideEffectSum, 10_000_000) + " msecs");//5ms
//
//        System.out.println("Parallel Range sum done in: " +
//                measureSumPerf(ParallelStream::parallelRangedSum, 10_000_000) + " msecs");//2ms
//
//        System.out.println("Range sum done in: " +
//                measureSumPerf(ParallelStream::rangedSum, 10_000_000) + " msecs");//10ms
//
//        System.out.println("Parallel sum done in: " +
//                measureSumPerf(ParallelStream::parallelSum, 10_000_000) + " msecs");//163ms
//
//        System.out.println("Sequential sum done in: " +
//                measureSumPerf(ParallelStream::sequentialSum, 10_000_000) + " msecs");//85ms


//        long l = parallelSum(100);
//        System.out.println(l);
//
//        long sum = sequentialSum(100);
//        System.out.println(sum);
    }

    /**
     * 计算10次n累加，获取最快的计算时间。
     * 1.Iterate generates迭代生成装箱，加法前需要拆箱(boxed object, which have to be unboxed to numbers before they can be added)
     * 2.Iterate 很难分成多个独立块并行执行。(difficult to divided into independent chunks to execute in parallel)
     *
     * @param adder
     * @param n
     * @return
     */
    public static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result:" + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    /**
     * parallel计算存在并发安全问题
     * @param n
     * @return
     */
    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    private static class Accumulator {
        public long total = 0;

        public void add(long value) {
            total += value;
        }
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).orElse(0);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).orElse(0);
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
    }


}
