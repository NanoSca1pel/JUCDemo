package com.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author lhtao
 * @date 2019-04-26 13:30
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws Exception{

        CountDownLatch latch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t国被灭");
                latch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();

        }

        latch.await();
        System.out.println();
        System.out.println(CountryEnum.ONE);
        System.out.println(CountryEnum.ONE.getRetCode());
        System.out.println(CountryEnum.ONE.getRetMessage());
        System.out.println(Thread.currentThread().getName() + "\t 秦国一统华夏");

    }


    public static void closeDoor() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 上晚自习，离开教室");
                latch.countDown();
            }, String.valueOf(i)).start();

        }

        latch.await();
        System.out.println(Thread.currentThread().getName() + "\t 班长最后关门走人");
    }
}
