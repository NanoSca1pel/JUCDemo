package com.juc;

import java.util.concurrent.*;

/**
 * @author lhtao
 * 第4种获得/使用java多线程的方式
 * @date 2019-04-29 13:21
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {

        ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 1L, TimeUnit.SECONDS,
                                                            new LinkedBlockingQueue<Runnable>(3), Executors.defaultThreadFactory(),
                                                            //new ThreadPoolExecutor.AbortPolicy()
                                                            //new ThreadPoolExecutor.CallerRunsPolicy()
                                                            //new ThreadPoolExecutor.DiscardOldestPolicy()
                                                            new ThreadPoolExecutor.DiscardPolicy()
                                                            );

        try {

            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                //try { TimeUnit.MILLISECONDS.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void jdkInitThreadPool() {
        //查看cpu线程数
        //System.out.println(Runtime.getRuntime().availableProcessors());

        //Array Arrays
        //Collection Collections
        //Executor Executors
        //所有带s的都是原来那个类的辅助工具类


        //ExecutorService threadPool = Executors.newFixedThreadPool(5); //一池谷固定个数处理线程
        //ExecutorService threadPool = Executors.newSingleThreadExecutor(); //一池1个处理现场
        ExecutorService threadPool = Executors.newCachedThreadPool(); //一池N个处理现场


        try {

            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                try { TimeUnit.MILLISECONDS.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
