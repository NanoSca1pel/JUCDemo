package com.gc;

import java.util.Random;

/**
 * @author lhtao
 *
 * 1. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC    (DefNew + Tenured)
 *
 * 2. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC    (ParNew + Tenured)
 *
 *     备注情况： Java HotSpot(TM) 64-Bit Server VM warning:
 *     Using the ParNew young collector with the Serial old collector is deprecated
 *     and will likely be removed in a future release
 *
 * 3. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC    (PSYoungGen + ParOldGen)
 *
 * 4.
 *  4.1 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC    (PSYoungGen + ParOldGen)
 *  不加就是默认UseParallelGC
 *  4.2 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags     (PSYoungGen + ParOldGen)
 *
 * 5. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC    (par new Generation(ParNew) + concurrent mark-sweep generation)
 *
 * 6. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC            后面单独讲G1GC
 *
 * 7. (理论知道即可，实际中java8开始已经被优化掉了，没有了)
 *    -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC
 *
 *
 * 下面是故意繁琐配置，主要是为了学习，一般生产不这么配置：
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC -XX:+UseParallelOldGC    (PSYoungGen + ParOldGen)
 *
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC -XX:+UseConcMarkSweepGC    (par new Generation(ParNew) + concurrent mark-sweep generation)
 *
 * @date 2019-05-10 11:10
 */
public class GCDemo {

    public static void main(String[] args){

        System.out.println("**********GCDemo hello");

        try{
            String str = "lht";
            while(true)
            {
                str += str + new Random().nextInt(777777777) + new Random().nextInt(22222222);
                str.intern();
            }
        } catch(Throwable e) {
            e.printStackTrace();
        }

    }
}
