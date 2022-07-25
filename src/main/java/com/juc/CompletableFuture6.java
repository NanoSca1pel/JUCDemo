package com.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * 解析不带Async和带Async方法的区别
 * 1.没有传入自定义线程池，都用默认线程池ForkJoinPool
 * 2.传入了一个自定义线程池
 *   如果执行第一个任务的时候，传入了一个自定义线程池：
 *   2.1 当调用thenRun方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池
 *   2.2 当调用thenRunAsync执行第二个任务时，则第一个任务使用的是自定义线程池，第二个任务使用的是ForkJoin线程池
 * 3.备注
 *   就算指定了自定义线程池，但是有时有可能处理太快，系统优化切换原则，会直接使用main线程处理
 * 其它如：thenAccept和thenAcceptAsync，thenApply和thenApplyAsync等，它们之间的区别也是同理
 *
 *
 * @author: lhtao
 * @date: 2022年07月25日 15:17
 */
public class CompletableFuture6 {

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        try{
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                    try { TimeUnit.MILLISECONDS.sleep(20); } catch(InterruptedException e) { e.printStackTrace(); }
                    System.out.println("1号任务\t" + Thread.currentThread().getName());
                    return "abcd";
                }, threadPool).thenRunAsync(() -> {
                    try { TimeUnit.MILLISECONDS.sleep(20); } catch(InterruptedException e) { e.printStackTrace(); }
                    System.out.println("2号任务\t" + Thread.currentThread().getName());
                }).thenRunAsync(() -> {
                    try { TimeUnit.MILLISECONDS.sleep(20); } catch(InterruptedException e) { e.printStackTrace(); }
                    System.out.println("3号任务\t" + Thread.currentThread().getName());
                }).thenRunAsync(() -> {
                    try { TimeUnit.MILLISECONDS.sleep(10); } catch(InterruptedException e) { e.printStackTrace(); }
                    System.out.println("4号任务\t" + Thread.currentThread().getName());
                });
            System.out.println(future.get(2, TimeUnit.SECONDS));
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
