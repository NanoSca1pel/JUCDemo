package com.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * thenCombine 两个任务合并
 * 两个CompletionStage任务都完成后，最终能把两个任务的结果一起交给thenCombine来处理
 * 先完成的先等着，等待其它分支任务
 *
 * @author: lhtao
 * @date: 2022年07月25日 15:17
 */
public class CompletableFuture8 {

    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> playA = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 启动");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> playB = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 启动");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });

        CompletableFuture<Integer> combine = playA.thenCombine(playB, (x, y) -> {
            System.out.println("结果合并");
            return x + y;
        });

        System.out.println(Thread.currentThread().getName() + "\t ----:" + combine.join());
    }
}
