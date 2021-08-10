package com.juc2021;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lhtao
 * @descript
 * @date 2021/8/9 15:48
 */
public class SaleTicket02 {

    public static void main(String[] args) throws Exception {

        LTicket ticket = new LTicket();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (ticket.number > 0) {
                    try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
                    ticket.sale();
                }
            }, i + "").start();
        }
    }
}

class LTicket {

    public int number = 30;

    //创建可重入锁
    private final ReentrantLock lock = new ReentrantLock();

    public void sale() {
        //上锁
        lock.lock();
        try {
            if (number > 0) {
                System.out.println("当前票剩余：" + (number--) + "," + Thread.currentThread().getName() + "卖出后剩余：" + number);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }
    }
}
