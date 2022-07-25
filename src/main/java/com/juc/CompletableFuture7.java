package com.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * applyToEither 多任务谁先执行完就取谁的结果
 *
 * @author: lhtao
 * @date: 2022年07月25日 15:17
 */
public class CompletableFuture7 {

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            System.out.println("A come in");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "playA";
        });

        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            System.out.println("B come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "playB";
        });

        CompletableFuture<String> result = playA.applyToEither(playB, f -> f + " is winner");
        System.out.println(Thread.currentThread().getName() + "\t ----:" + result.join());
    }
}
