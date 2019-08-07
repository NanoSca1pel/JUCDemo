package com.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lhtao
 * volatile可见性及不保证原子性验证
 * @date 2019-04-22 14:08
 */

class MyData{

    volatile int number = 0;

    public synchronized void addTo60(){
        this.number = 60;
    }

    /** 请注意，此时number前面是加了volatile关键字修饰的， volatile不保证原子性*/
    public void addPlusPlus(){
        number++;
    }

    /** 默认初始值0*/
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}

/**
 * 1. 验证volatile的可见性
 *  1.1 假如 int number = 0; number变量之前根本没有添加volatile关键字修饰,没有可见性
 *  1.2 添加了volatile可以解决可见性问题
 *
 * 2. 验证volatile不保证原子性
 *  2.1 原子性指的是什么意思？
 *          不可分割，完整性， 也即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割。需要整体完整
 *          要么同时成功，要么同时失败。
 *  2.2 volatile不保证原子性的案例演示
 *  2.3 why
 *  2.4 如何解决原子性？
 *      * 加synchronized
 *      * 使用我们的juc包下的AtomicInteger 原子包装类
 *
 */
public class VolatileDemo {

    public static void main(String[] args){

        MyData myData = new MyData();
        for (int i=1; i<=20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();

                }
            }, String.valueOf(i)).start();
        }

        /** 需要等待上面20个线程都全部计算完成后，再用main线程取得最终的结果值看是多少？*/
        while(Thread.activeCount() > 2) {

            Thread.yield();

        }
        System.out.println(Thread.currentThread().getName() + "\t int finally number value: " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t atomicInteger finally number value: " + myData.atomicInteger);
    }

    /** volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改。*/
    public static void seeOkByVolatile() {
        //线程操作资源类
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            //暂停一会线程
            try { TimeUnit.SECONDS.sleep(3); } catch(InterruptedException e) { e.printStackTrace(); }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t update number value: " + myData.number);
        }, "AAA").start();

        //第二个线程就是我们的main线程
        while(myData.number == 0){
            //main线程就一直在这等待循环，直到number的值不再等于0
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over, main get number value: " + myData.number);
    }
}
