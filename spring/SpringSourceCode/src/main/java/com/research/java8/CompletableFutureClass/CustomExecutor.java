package com.research.java8.CompletableFutureClass;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

/**
 * N(threads)=N(cpu)*U(cpu)*(1+W/C)
 * N(threads):线程数
 * N(cpu):Runtime.getRuntime().availableProcessors()*
 * U(cpu):target CPU utilization(between 0 and 1)
 * W/C: ratio of wait time to computer time
 */
public class CustomExecutor {
    private static final List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"));

    // 自定义线程池大小
    private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public static void main(String[] args) {

        long start = System.nanoTime();
        System.out.println(findPricesAsyn(shops, "MyPhone20S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Duration: " + duration + " msec");

    }

    // 并行异步执行find all prices
    public static List<String> findPricesAsyn(List<Shop> shops, String product) {
        List<CompletableFuture<String>> list = shops.parallelStream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor))
                .collect(Collectors.toList());
        return list.parallelStream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


}
