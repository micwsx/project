package com.research.java8.CompletableFutureClass;

import ch.qos.logback.core.util.TimeUtil;
import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Michael
 * @create 11/20/2020 11:18 AM
 */
public class AsynchronousDemo {

    public static void main(String[] args) {

        // along_lasting_operation();
    }


    public static double getPrice(String product) {
        return calculatePrice(product);
    }

    public static double calculatePrice(String product) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextDouble() * product.charAt(0) * product.charAt(1);
    }

    public static Future<Double> getPriceAsyn(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    public static Future<Double> getPrice(Shop shop, String product) {
        CompletableFuture<Double> futurePriceInUSD = CompletableFuture.supplyAsync(() -> shop.getPrice(product))
                .thenCombine(CompletableFuture.supplyAsync(() -> 0.78D), (price, rate) -> price * rate);
        return futurePriceInUSD;
    }

    public static Future<Double> getPriceAsynByStaticFactoryMethod(String product) {
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
            double price = calculatePrice(product);
            return price;
        });
        return future;
    }


    public static void along_lasting_operation() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(() -> {
            doSomeLongComputation();
            return 0.2D;
        });

        System.out.println("do something else.");
        long startTime = System.nanoTime();
        try {
            System.out.println("waiting result...");
            Double result = future.get();
            long duration = (System.nanoTime() - startTime) / 1_000_000_000;
            System.out.println("耗时：" + duration + "s;获取结果：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void doSomeLongComputation() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
