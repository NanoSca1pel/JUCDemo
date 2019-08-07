package com.juc;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程在高内聚低耦合的情况下是 线程操纵资源类
 */
@ToString
class MyCache{

    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //缓存框架三大基本功能  读get  写put  清空clear
    //写入
    public void put(String key, Object value) {

        readWriteLock.writeLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
            //暂停一会线程 模拟网络拥堵的情况
            try { TimeUnit.MILLISECONDS.sleep(300); } catch(InterruptedException e) { e.printStackTrace(); }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成！");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    //读取
    public void get(String key) {
        readWriteLock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取！");
            //暂停一会线程 模拟网络拥堵的情况
            try { TimeUnit.MILLISECONDS.sleep(300); } catch(InterruptedException e) { e.printStackTrace(); }
            Object obj = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + obj);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }
    }

    //清楚缓存
    public void clearMap(){
        map.clear();
    }
}


/**
 * @author lhtao
 *
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程想去写共享资源，就不应该再有其它线程可以对该资源进行读或写
 * 小总结：
 *      读-读能共存
 *      读-写不能共存
 *      写-写不能共存
 *
 *      写操作：原子+独占，整个过程必须是一个完整的统一体，中间不允许被分割，打断
 *
 * @date 2019-04-25 16:27
 */
public class ReadWriteLockDemo {

    public static void main(String[] args){

        MyCache myCache = new MyCache();

        for (int i=1; i<=5; i++) {

            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }


        for (int i=1; i<=5; i++) {

            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }

    }
}
