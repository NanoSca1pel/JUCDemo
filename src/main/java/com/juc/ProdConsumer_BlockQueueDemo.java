package com.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{

    //默认开启，进行生产+消费
    //高并发情况下易变的变量使用volatile关键字修饰
    private volatile boolean FLAG = true;

    //高并发使用原子引用，不要使用i++ i--等自增自减操作。无法保证数据原子性
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws Exception{

        String data = null;
        boolean retValue;
        while(FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            retValue =  blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列数据" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列数据" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 大老板叫停，表示FLAG=false，生产动作结束。");
    }


    public void myConsumer() throws Exception {

        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒没有取到蛋糕，消费退出");
                return;
            }


            System.out.println(Thread.currentThread().getName() + "\t 消费队列蛋糕" + result + "成功");


        }
    }

    public void stop() throws Exception {
        this.FLAG = false;
    }
}


/**
 * @author lhtao
 *
 * volatile/CAS/atomicInteger/原子引用/BlocingQueue/线程交互
 *
 * @date 2019-04-28 16:11
 */
public class ProdConsumer_BlockQueueDemo {

    public static void main(String[] args) throws Exception {
        MyResource resource = new MyResource(new ArrayBlockingQueue<>(3));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try {
                resource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Prod").start();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try {
                resource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        //暂停主线程5秒钟
        try { TimeUnit.SECONDS.sleep(5); } catch(InterruptedException e) { e.printStackTrace(); }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒钟时间到，大老板main线程叫停");

        resource.stop();
    }
}
