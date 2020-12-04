package com.research.java8.CompletableFutureClass;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Contact multiple online shops to find the lowest price for a given product or service.
 */
public class BestPriceFinder {

    public static void main(String[] args) {

        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice"),
                new Shop("LetSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"));

        long start = System.nanoTime();
//        System.out.println(findPrices(shops, "MyPhone27S"));//4042
//        System.out.println(findPricesAsyn(shops, "MyPhone27S"));
        System.out.println(findPricesAsynJoin(shops, "MyPhone27S"));//1065
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("duration: " + duration + " msec");

//       SingleRequest();
    }




    public static List<String> findPricesAsyn2(List<Shop> shops, String product) {
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        List<CompletableFuture<String>> completableFutureList = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price:" + shop.getPrice(product), executorService))
                .collect(Collectors.toList());
        return completableFutureList.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


    public static List<String> findPricesAsynJoin(List<Shop> shops, String product) {
        List<CompletableFuture<String>> pricesAsyn = findPricesAsyn(shops, product);
        return pricesAsyn.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    // 并行异步执行find all prices
    public static List<CompletableFuture<String>> findPricesAsyn(List<Shop> shops, String product) {
        List<CompletableFuture<String>> list = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        return list;
    }

    // 并行执行find all prices
    public static List<String> findPricesParallelStream(List<Shop> shops, String product) {
        List<String> list = shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
        return list;
    }

    // 同步执行find all prices
    public static List<String> findPrices(List<Shop> shops, String product) {
//        List<String> list = shops.stream().collect(
//                Collectors.mapping(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)),
//                        Collectors.toList()));
        List<String> list = shops.stream().map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
        return list;
    }


    private static void SingleRequest() {
        Shop shop = new Shop();
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("Ice cream");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after: " + invocationTime + " msecs");

        System.out.println("do something else");

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Price returned after: " + retrievalTime + " msecs");
    }
}
