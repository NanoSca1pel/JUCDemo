package com.juc;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author lhtao
 * @descript
 * @date 2021/8/10 10:21
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws Exception {
        //创建MyTask对象
        MyTask myTask = new MyTask(0, 100);
        //创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> fjTask = forkJoinPool.submit(myTask);
        //获取最终的合并结果
        Integer result = fjTask.get();
        System.out.println(result);
        //关闭池对象
        forkJoinPool.shutdown();
    }
}

class MyTask extends RecursiveTask<Integer> {

    private static final int value = 10;

    private int begin;

    private int end;

    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    //拆分和合并过程
    @Override
    protected Integer compute() {
        //判断相加的两个数值是否大于10
        if ((end - begin) <= value) {
            //相加操作
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            //进一步拆分
            int middle = ((Double)Math.ceil((end + begin) / 2)).intValue();
            //拆分左边
            MyTask task01 = new MyTask(begin, middle);
            //拆分右边
            MyTask task02 = new MyTask(middle + 1, end);

            task01.fork();
            task02.fork();
            //合并结果
            result = task01.join() + task02.join();
        }
        return result;
    }
}
