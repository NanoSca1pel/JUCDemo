package com.juc;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable {

    private String lockA;

    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {

        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有"+ lockA + ",妄图持有" + lockB);
            
            try { TimeUnit.SECONDS.sleep(1); } catch(InterruptedException e) { e.printStackTrace(); }

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有"+ lockB + ",妄图持有" + lockA);
            }
        }
    }
}


/**
 * @author lhtao
 *
 * 两个或者两个以上的进程在执行过程中，因争夺资源而造成的一种相互等待的现象，若无外力干涉，那它们都将无法推进下去，
 * 如果系统资源充足，进程的资源请求都能够得到满足，死锁出现的可能性就很低，否则就会因为争夺有限的资源而陷入死锁。
 * @date 2019-04-30 14:26
 */
public class DeadLockDemo {

    public static void main(String[] args){

        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA, lockB), "ThreadA").start();
        new Thread(new HoldLockThread(lockB, lockA), "ThreadB").start();


        /**
         * linux  ps -ef|grep xxxx          ls -l
         * window下的java运行程序    也有类似ps查看进程的命令，但是我们目前需要查看的只有java程序
         *        jps = java ps             jsp -l
         *
         */

    }

}
