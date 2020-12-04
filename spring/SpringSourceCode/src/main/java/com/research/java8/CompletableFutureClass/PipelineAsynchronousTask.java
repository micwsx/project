package com.research.java8.CompletableFutureClass;

import com.sun.org.apache.xpath.internal.operations.Quo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class PipelineAsynchronousTask {

    private static final List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"));

    public static void main(String[] args) {
        long start = System.nanoTime();
//        finalPriceParallel();
        System.out.println(finalPriceAsyn("MyPhone20S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Duration: " + duration + " msec");
    }

    private static List<String> finalPriceAsyn(String product) {
        List<CompletableFuture<String>> priceFuture = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithCode(product)))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote))))
                // .toArray(size -> new CompletableFuture[size]);
                .collect(Collectors.toList());
        return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }


    private static void finalPriceParallel() {
        List<String> finalPriceList = shops.parallelStream()
                .map(shop -> shop.getPriceWithCode("MyPhone20S"))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
//              .collect(Collectors.mapping(Discount::applyDiscount, Collectors.toList()));
        System.out.println(finalPriceList);
    }

    private static List<String> findPrices(String product) {
        List<CompletableFuture<String>> list = shops.stream()
                .map(shop ->
                        CompletableFuture.supplyAsync(() -> shop.getPriceWithCode(product))
                ).collect(Collectors.toList());
        return list.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }


}
