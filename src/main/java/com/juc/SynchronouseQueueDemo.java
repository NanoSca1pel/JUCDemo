package com.juc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author lhtao
 * SynchronouseQueueDemo中只能存储一个元素，只有当队列中元素被取走之后才能重新插入。否则线程会被阻塞等待
 * @date 2019-04-28 13:46
 */
public class SynchronouseQueueDemo {

    public static void main(String[] args) throws InterruptedException{

        BlockingQueue<String> blockingQueue = new SynchronousQueue();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                blockingQueue.put("1");

                System.out.println(Thread.currentThread().getName() + "\t put 2");
                blockingQueue.put("2");

                System.out.println(Thread.currentThread().getName() + "\t put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();


        new Thread(() -> {
            try {
                try { TimeUnit.SECONDS.sleep(5); } catch(InterruptedException e) { e.printStackTrace(); }
                System.out.println(blockingQueue.take());

                try { TimeUnit.SECONDS.sleep(5); } catch(InterruptedException e) { e.printStackTrace(); }
                System.out.println(blockingQueue.take());

                try { TimeUnit.SECONDS.sleep(5); } catch(InterruptedException e) { e.printStackTrace(); }
                System.out.println(blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
