package com.juc;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Description: 自定义任务定时器
 * @author: lhtao
 * @date: 2022年05月11日 13:09
 */
public class MyTimerDemo {

    public static void main(String[] args) {
        MyTimer myTimer = new MyTimer();
        myTimer.schedule(new Runnable() {//前面的参数是传入了一个匿名对象
            @Override
            public void run() {
                System.out.println("执行");
            }
        },3000);
        System.out.println("main");
    }
}

//创建一个类，表示一个任务
class MyTasks implements Comparable<MyTasks>{ //实现Comparable接口，设定比较规则
    //任务具体要干什么
    private Runnable runnable;
    //任务具体啥时候干，保存任务要执行的毫秒级时间戳
    private long time;

    //提供一个构造方法
    public MyTasks(Runnable runnable, long delay) { //delay是一个时间间隔，不是绝对的时间戳的值
        this.runnable = runnable;
        this.time = System.currentTimeMillis() + delay;
    }

    public void run(){//这里不是Runnable里面的run方法，这里只是自己定义了一个任务类，这个run指的是任务执行的方法
        runnable.run();
    }

    public long getTime() {
        return time;
    }

    @Override
    public int compareTo(MyTasks o) {//这个方法的实现是在建堆的时候找最小值的比较过程中，并没有通过此处的代码进行实现
        //让时间小的在前，时间大的在后
        return (int)(this.time - o.time);
    }
}

//定时器
class MyTimer {
    private PriorityBlockingQueue<MyTasks> queue = new PriorityBlockingQueue<>();

    private Object locker = new Object();

    //使用schedule方法来注册任务到队列中
    public void schedule(Runnable runnable, long delay) {
        MyTasks task = new MyTasks(runnable, delay);
        queue.put(task);
        //每次任务插入成功之后，都唤醒一下扫描线程，让线程重新检查一下队首的任务，看是否时间到了要执行
        synchronized (locker) {
            locker.notify();
        }
    }

    //创建一个扫描线程
    public MyTimer() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    //先取出队首元素
                    MyTasks task = queue.take();
                    long curTime = System.currentTimeMillis();
                    //判断一下时间是否到达
                    if (curTime < task.getTime()) {
                        //时间没到，把任务塞回到队列中
                        queue.put(task);
                        //指定一个等待时间
                        synchronized (locker) {
                            //当执行任务但没有被notify新插入任务唤醒的时候，阻塞到这里
                            locker.wait(task.getTime() - curTime);
                        }
                    } else {
                        //时间到了，执行任务
                        task.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}