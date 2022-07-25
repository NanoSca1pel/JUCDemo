package com.juc;

import java.util.concurrent.*;

/**
 * @Description:
 * 对结果进行处理
 * thenApply 计算结果存在依赖关系，线程存在串行化。
 *           由于存在依赖关系（当前步错，不会走下一步），当前步骤有异常的话就叫停
 * handle 计算结果存在依赖关系，线程存在串行化
 *        handle有两个入参，可以获得上一步的异常。 有异常也可以往下走，根据带的异常参数可以进一步处理
 *
 * 对结果进行消费(接收任务的处理结果，并消费处理，无返回结果)
 * thenRun(Runnable runnable) 任务A执行完执行B，并且B不需要A的结果
 * thenAccept(Consumer action) 任务A执行完执行B，B需要A的结果，但是任务B无返回值
 * thenApply(Function fn) 任务A执行完执行B，B需要A的结果，同时任务B有返回值
 *
 * exceptionally 相当于 try/catch
 * whenComplete+handle 相当于 try/catch/finally
 * @author: lhtao
 * @date: 2022年07月25日 14:17
 */
public class CompletableFutureDemo5 {

    public static void main(String[] args) throws Exception {

        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        //CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
        //    try {
        //        TimeUnit.SECONDS.sleep(1);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //    System.out.println("一阶段");
        //    return 1;
        //}, threadPool).thenApply(r -> {
        //    //int i = 10/0;
        //    System.out.println("二阶段");
        //    return r + 2;
        //}).thenApply(r -> {
        //    System.out.println("三阶段");
        //    return r + 3;
        //}).whenComplete((r, e) -> {
        //    if (e == null) {
        //        System.out.println("计算结果:" + r);
        //    }
        //}).exceptionally(e -> {
        //    e.printStackTrace();
        //    return null;
        //});

        /*CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("一阶段");
            return 1;
        }, threadPool).handle((r,e) -> {
            int i = 10/0;
            System.out.println("二阶段");
            return r + 2;
        }).handle((r,e) -> {
            if (e != null) {
                System.out.println(e.getMessage());
            }
            System.out.println("三阶段");
            return r + 3;
        }).whenComplete((r, e) -> {
            if (e == null) {
                System.out.println("计算结果:" + r);
            }
        }).handle((r,e) -> {
            return r;
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });*/

        /*CompletableFuture<Void> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("一阶段");
            return 1;
        }, threadPool).thenApply(r -> {
            //int i = 10/0;
            System.out.println("二阶段");
            return r + 2;
        }).thenApply(r -> {
            System.out.println("三阶段");
            return r + 3;
        }).thenAccept(System.out::println);

        System.out.println(Thread.currentThread().getName() + "先去忙别的任务了");
        if (!threadPool.isShutdown()) {
            threadPool.shutdown();
        }*/

        System.out.println(CompletableFuture.supplyAsync(() -> {
            System.out.println("一阶段");
            return 1;
        }).thenRun(() -> System.out.println("二阶段")));

        System.out.println("===========================");

        System.out.println(CompletableFuture.supplyAsync(() -> {
            System.out.println("一阶段");
            return 1;
        }).thenAccept((r) -> System.out.println(r)).join());

        System.out.println("===========================");

        System.out.println(CompletableFuture.supplyAsync(() -> {
            System.out.println("一阶段");
            return 1;
        }).thenApply((r) -> {
            System.out.println(r);
            return r + 2;
        }).join());
    }
}
