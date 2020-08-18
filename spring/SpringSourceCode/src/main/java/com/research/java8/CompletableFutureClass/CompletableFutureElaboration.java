package com.research.java8.CompletableFutureClass;

import java.util.concurrent.*;

/**
 * Creating an asynchronous computation and retrieving its result.
 * Increase throughput using non-blocking operations.
 * Designing and implementing an asynchronous API
 * Consuming asynchronously a synchronous API
 * Pipelining and merging two or more asynchronous operations.
 * Reacting to the completion of an asynchronous operation.
 */
public class CompletableFutureElaboration {

    public static void main(String[] args) {
        executeLong_lastingOperationAsynchronouslyInAFuture();

    }

    public static void executeLong_lastingOperationAsynchronouslyInAFuture() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomethingLongLasting();
            }
        });

        doSomethingElse();

        try {
            // 阻塞获取结果
            Double result = future.get(1, TimeUnit.MINUTES);
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static double doSomethingLongLasting() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100;
    }

    private static void doSomethingElse() {
        System.out.println("do something else...");
    }

}
