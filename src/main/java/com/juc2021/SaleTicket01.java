package com.juc2021;

import java.util.concurrent.TimeUnit;

/**
 * @author lhtao
 * @descript
 * @date 2021/8/9 15:48
 */
public class SaleTicket01 {

    public static void main(String[] args) throws Exception {

        Ticket ticket = new Ticket();

        new Thread(() -> {
            while (ticket.number > 0) {
                ticket.sale();
            }
        }, "A").start();

        new Thread(() -> {
            while (ticket.number > 0) {
                ticket.sale();
            }
        }, "B").start();

        new Thread(() -> {
            while (ticket.number > 0) {
                ticket.sale();
            }
        }, "C").start();
    }
}

class Ticket {

    public int number = 30;

    public synchronized void sale() {
        if (number > 0) {
            System.out.println("当前票剩余：" + (number--) + "," + Thread.currentThread().getName() + "卖出后剩余：" + number);
        }
    }
}
