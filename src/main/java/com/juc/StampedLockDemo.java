package com.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock类似读写锁，支持优化读模式
 * 优化读基于假设，大多数情况下读操作并不会和写操作冲突，其逻辑是先试着修改，然后通过validate方法确认是否进入写模式
 * 如果没有进入，就成功避免开销；如果进入，则尝试获取读锁
 */
public class StampedLockDemo {

    private volatile Map<String, Object> map = new HashMap<>();

    private StampedLock stampedLock = new StampedLock();

    //缓存框架三大基本功能  读get  写put  清空clear
    //写入
    public void put(String key, Object value) {

        long stamp = stampedLock.writeLock();
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
            stampedLock.unlockWrite(stamp);
        }
    }

    //读取
    public void get(String key) {
        long stamp = stampedLock.tryOptimisticRead();
        if(!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
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
                stampedLock.unlockRead(stamp);
            }
        }
    }

    //清楚缓存
    public void clearMap(){
        map.clear();
    }
}
