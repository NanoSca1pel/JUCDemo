package com.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author lhtao
 * @date 2019-04-26 14:04
 */
public class SemaphoreDemo {

    public static void main(String[] args){

        //模拟3个停车位
        Semaphore semaphore = new Semaphore(3);

        //6部车开始抢车位
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    //暂停一会线程 模拟车辆停车时间
                    try { TimeUnit.SECONDS.sleep(3); } catch(InterruptedException e) { e.printStackTrace(); }
                    System.out.println(Thread.currentThread().getName() + "\t 停车3秒后里开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放停车位
                    semaphore.release();
                }
            }, String.valueOf(i)).start();

        }
    }
}
