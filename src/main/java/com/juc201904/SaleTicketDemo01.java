package com.juc201904;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 售票
 * 线程操作资源类
 * @author lhtao
 * @date 2020/4/1 15:45
 */
public class SaleTicketDemo01 {

    public static void main(String[] args) throws Exception {

        Ticket ticket = new Ticket();

        new Thread(() -> {
            for(int i=1; i<=40; i++) {
                ticket.sale();
            }
        }, "A").start();

        new Thread(() -> {
            for(int i=1; i<=40; i++) {
                ticket.sale();
            }
        }, "B").start();

        new Thread(() -> {
            for(int i=1; i<=40; i++) {
                ticket.sale();
            }
        }, "C").start();

    }
}



class Ticket {

    private int num = 300;

    private Lock lock = new ReentrantLock();

    public void sale() {

        lock.lock();
        try
        {
            if (num > 0) {
                System.out.println("当前票数：" + num-- + "，售后剩余票数：" + num + "，购票者:" + Thread.currentThread().getName());
            } else {
                System.out.println("票已全部售出，请等待下次抢购");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}