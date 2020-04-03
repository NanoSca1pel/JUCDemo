package com.juc201904;

/**
 *
 * 题目：现在两个线程。可以操作初始值为零的一个变量
 * 实现一个线程对该变量+1，一个线程对该变量-1
 * 实现交替，来10轮，变量初始值为零
 *
 * ① 高内聚低耦合前提下，线程操作资源类
 * ② 判断/干活/通知
 *
 * @author lhtao
 * @date 2020/4/3 15:05
 */
public class ProdConsumerDemo {

    public static void main(String[] args) throws Exception {

        Aircondition aircondition = new Aircondition();

        new Thread(() -> {
            for(int i=1; i<=10; i++) {
                try {
                    aircondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for(int i=1; i<=10; i++) {
                try {
                    aircondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for(int i=1; i<=10; i++) {
                try {
                    aircondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for(int i=1; i<=10; i++) {
                try {
                    aircondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}


class Aircondition {


    private int num = 0;

    public synchronized void increment() throws Exception {
        //1.判断
        if (num != 0) {
            this.wait();
        }

        //2.干活
        num ++;

        //3.通知
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        this.notifyAll();
    }

    public synchronized void decrement() throws Exception {
        //1.判断
        if (num != 1) {
            this.wait();
        }

        //2.干活
        num --;

        //3.通知
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        this.notifyAll();
    }
}
