package com.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareSource{

    private int number = 1; //A:1 B:2 C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();


    public void print5() {
        lock.lock();
        try
        {
            //1.判断
            while(number != 1) {
                c1.await();
            }
            //2.干活
            for (int i = 1; i < 6; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 2;
            c2.signal();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try
        {
            //1.判断
            while(number != 2) {
                c2.await();
            }
            //2.干活
            for (int i = 1; i < 11; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 3;
            c3.signal();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try
        {
            //1.判断
            while(number != 3) {
                c3.await();
            }
            //2.干活
            for (int i = 1; i < 16; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3.通知
            number = 1;
            c1.signal();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}


/**
 * @author lhtao
 *
 * 题目： 多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
 * AA打印5次， BB打印10次， CC打印15次
 * 紧接着
 * AA打印5次， BB打印10次， CC打印15次
 * ......
 * 来10轮
 * @date 2019-04-28 15:07
 */
public class SyncAndReentrantLockDemo {

    public static void main(String[] args){

        ShareSource shareSource = new ShareSource();
        new Thread(() -> {
            for (int i = 1; i < 11; i++) {
                shareSource.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i < 11; i++) {
                shareSource.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i < 11; i++) {
                shareSource.print15();
            }
        }, "C").start();
    }
}
