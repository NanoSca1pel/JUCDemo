package com.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description: 自定义线程池
 * @author: lhtao
 * @date: 2022年05月11日 13:53
 */
public class MyThreadPoolDemo2 {

    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool(10);
        final boolean[] flag = {true};
        for (int i = 0; i < 100; i++) {
            int c = i;
            myThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("hello myThreadPool" + c);
                    flag[0] = false;
                }
            });
        }
        while (flag[0]) {

        }
        System.out.println("主线程结束");
    }
}

class MyThreadPool {
    //1.描述一个任务，直接使用Runnable
    //2.使用一个数据结构来组织任务
    private BlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();
    //3.描述一个线程，工作线程的功能就是从任务队列中取任务并执行(这里是一个静态内部类)
    static class Worker extends Thread{
        //当前线程池中有若干个Worker线程，这些线程内部都持有上述的任务队列
        private BlockingDeque<Runnable> queue = null;
        public Worker(BlockingDeque<Runnable> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            while (true){
                try {
                    //我们没有办法直接使用第9行的queue，因为这是另一个类，所以我们在13行安排了这个变量，
                    // 并且调用worker构造方法把上面第九行的queue给传进来，让worker线程自身持有着这个队列
                    //循环的去获取任务队列的任务，
                    //如果队列为空就直接阻塞，如果队列非空，就获取到里面的内容
                    Runnable runnable = queue.take();
                    //获取到之后，就执行任务
                    runnable.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //4.创建一个数据结构来组织若干个线程
    private List<Thread> workers = new ArrayList<>();
    public MyThreadPool(int n){
        //构造方法中创建出若干个线程，放到上述的数组中
        for (int i = 0; i < n; i++) {
            Worker worker = new Worker(queue);//把上述的线程任务传到这一个结构数组中
            workers.add(worker);
            //将线程启动
            worker.start();
        }
    }
    //5.创建一个方法，允许程序员放任务到线程池当中
    public void submit(Runnable runnable){
        try {//把这个runnale 任务加到queue中
            queue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
