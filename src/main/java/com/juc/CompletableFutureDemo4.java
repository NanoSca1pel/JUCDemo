package com.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * 结果获取和触发计算
 * @author: lhtao
 * @date: 2022年07月25日 10:47
 */
public class CompletableFutureDemo4 {

    public static void main(String[] args) { //throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc";
        });

        //System.out.println(future.get());   //不见不散,没有结果会阻塞，直至获得结果
        //System.out.println(future.get(2, TimeUnit.SECONDS));  //过时不候，超过等待时间没有结果则抛出超时异常

        //System.out.println(future.join()); //作用和get一样

        try { TimeUnit.SECONDS.sleep(4); } catch(InterruptedException e) { e.printStackTrace(); }
        //System.out.println(future.getNow("xxx")); //如果没有获取到值，则返回xxx，否则直接返回获得的结果
        System.out.println(future.complete("xxx") + "\t" + future.join()); //如果没有完成，中途打断了，则返回true，此时get/join只能获得备用值。 如果完成了，则返回false，此时get/join可以得到结果值
    }
}