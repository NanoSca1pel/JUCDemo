package com.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone  implements Runnable{

    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t ######invoked sendEmail()");
    }

    //默认是false
    Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get()");
            set();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t set()");
        } finally {
            lock.unlock();
        }
    }
}


/**
 * @author lhtao
 * 可重入锁（递归锁）
 * synchronized/ReentrantLock都是可重入锁
 *
 * 指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 * 在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * 也就是说，线程可以进入任何一个它已经拥有的锁 所同步着的代码块。
 *
 * t1	 invoked sendSMS()              t1线程在外层方法获取锁的时候
 * t1	 ######invoked sendEmail()      t1在进入内层方法会自动获取锁
 * t2	 invoked sendSMS()
 * t2	 ######invoked sendEmail()
 *
 *
 *
 *
 * t3	 get()
 * t3	 set()
 * t4	 get()
 * t4	 set()
 *
 * 只要加锁和解锁的数量匹配，无论多少锁都不会有影响。
 * 加锁和解锁数量不匹配时，编译可通过，运行不报错，但是会造成死锁，导致程序卡死。
 *
 * @date 2019-04-25 15:12
 */
public class ReentrantLockDemo {

    public static void main(String[] args){

        Phone phone = new Phone();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();

        try { TimeUnit.SECONDS.sleep(3); } catch(InterruptedException e) { e.printStackTrace(); }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        Thread t3 = new Thread(phone, "t3");
        Thread t4 = new Thread(phone, "t4");

        t3.start();
        t4.start();

    }
}

