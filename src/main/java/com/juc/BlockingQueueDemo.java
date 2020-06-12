package com.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author lhtao
 *
 * ArrayBlockingQueue： 是一个基于数组结构的有界阻塞队列，此队列按FIFO（先进先出）的原则对元素进行排序。
 * LinkedBlockingQueue： 是一个基于链表结构的阻塞队列，此队列按FIFO（先进先出）排序原则，吞吐量通常要高于ArrayBlockingQueue。
 * SynchronouseQueue： 是一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作。否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue
 *
 *
 * 1.队列  排队打饭
 *
 * 2.阻塞队列
 *  2.1 阻塞队列的优点
 *
 *  2.2 不得不阻塞，如何管理
 *
 * @date 2019-04-26 14:24
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws Exception{

        //List list = null;
        //需要填初始值，告诉阻塞队列有界范围
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        //第一组
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        //Exception in thread "main" java.lang.IllegalStateException: Queue full
        //队列已满异常
        //System.out.println(blockingQueue.add("d"));

        //检查队列是否为空，返回队首元素
        System.out.println(blockingQueue.element());

        //如果不填参数，则默认清除a  先进先出。 如果加了参数 则是定点删除
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        //Exception in thread "main" java.util.NoSuchElementException
        //队列为空后无法再删除元素
        //System.out.println(blockingQueue.remove());

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        //第二组
        //插入队列，如果超过队列边界，返回false
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("x"));

        //返回队首元素
        System.out.println(blockingQueue.peek());

        //从队首开始取元素，如果无元素可取，返回null
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        //第三组
        //如果队列已满，当还有元素要进入队列，则被阻塞。直到队列中有元素出去，留出空位
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
        System.out.println("==============");
        //blockingQueue.put("x");

        //如果队列已空，当还需要从队列中取元素时，也会被阻塞。直到队列中进入新的数据
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        //blockingQueue.take();


        //比较推荐的方法
        //插入数据的时候限制插入阻塞时间，超过时间后队列中仍然没有空位则放弃插入
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));

    }
}
