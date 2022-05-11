package com.juc;

/**
 * @Description: 自定义的阻塞队列
 * @author: lhtao
 * @date: 2022年05月11日 10:38
 */
public class MyBlockingQueue {
    //基于数组来实现阻塞队列
    private int[] tmp = new int[1000];
    //队列长度
    private int size = 0;
    //队首元素下标
    private int start = 0;
    //队尾元素下标
    private int end = 0;
    //创建一个对象，以便后续进行锁
    private  Object locker = new Object();
    //实现入队列的操作
    public void put(int val) throws InterruptedException {
        //队列为满的情况进行阻塞
        synchronized (locker) {
            if (size == tmp.length) {
                locker.wait();
            }
            //当队列不为满的时候，每入队一个元素，end向后移一位
            tmp[end] = val;
            end++;
            //当start到达末尾，那么说明该次循环已经结束，就要进入新的循环就需要重新进行指定
            if (end >= tmp.length) {
                end = 0;
            }//每成功入队一个元素，那么，size的个数就要加加
            size++;
            //这里的notify（）是用于解锁于队列为空而不能进一步出队列的操作。
            locker.notify();
        }
    }
    //实现出队的操作
    public Integer take() throws InterruptedException {
        synchronized (locker) {
            //遇到空队列进行阻塞
            if (size == 0) {
                locker.wait();
            }
            //当队列不为空时，出元素
            int ret = tmp[start];
            start++;
            //要是start到了队尾说明已经结束了,置为0并开始新的循环
            if (start >= tmp.length) {
                start = 0;
            }
            //每出队一个元素，对应的size--
            size--;
            //当size减了之后说明不再是满队列了，可以向里面放入新的元素了，阻塞结束
            //这里notify解锁用于队列为满而不能进一步入队的操作
            locker.notify();
            //返回出队的元素
            return ret;
        }
    }
}

class TestMyBlockingQueue {
    public static void main(String[] args) {
        //阻塞队列的创建
        MyBlockingQueue queue = new MyBlockingQueue();
        //实现一个生产者消费者模式
        Thread t = new Thread(() -> {
            int num = 0;
            while (true) {
                System.out.println("生产了：" + num);
                try {
                    queue.put(num);
                    //这里保持生产与消费步调的一致
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num++;
            }
        });
        t.start();
        Thread t2 = new Thread(() -> {
            int num = 0;
            while (true) {
                System.out.println("消费了：" + num);
                try {
                    num = queue.take();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
    }
}
