package com.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

        System.out.println("********** come in Callable");
        //暂停一会线程，模拟计算时间
        try { TimeUnit.SECONDS.sleep(2); } catch(InterruptedException e) { e.printStackTrace(); }
        
        return 1024; //Callable有返回值，返回值的类型就是Callable的参数类型
    }
}

/**
 * @author lhtao
 *
 * 1.FutureTask.get()方法 建议放在最后，给线程计算时间。 如果立马获取结果，会导致堵塞。等待线程计算完成才能执行下面的方法
 * 2.一个FutureTask 有两个线程运行，则call方法只会进入一次。 想要进入两次，需要两个FutureTask。 FutureTask与线程一一对应
 *
 * @date 2019-04-29 10:19
 */
public class CallableDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //两个线程，一个main主线程， 一个是 AAfutureTask

        //FutureTask的父类即实现了Runable 又有Callable为参数的构造方法
        //Future(Callable<V> callable)
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());

        new Thread(futureTask, "AA").start();
        //int data02 = futureTask.get(); //会导致堵塞，整个程序等待AA线程计算完成才执行后面的方法

        //如果只有一个FutureTask。有两个线程执行相同的方法，那么call方法只会进去一次。 除非有另外的FutureTask2
        new Thread(futureTask, "BB").start();


        //FutureTask<Integer> futureTask2 = new FutureTask<>(new MyThread());
        //new Thread(futureTask2, "BB").start();

        System.out.println(Thread.currentThread().getName() + "**********");


        int data01 = 100;

        while(!futureTask.isDone()) {
            //如果futureTask没有完成，则一直循环，直至任务完成。类似自旋锁
        }

        //要求获得Callable线程的计算结果，如果没有计算完成就去强求，会导致堵塞，直至计算完成
        int data02 = futureTask.get();

        System.out.println("******result:" + (data01 + data02));

    }
}
