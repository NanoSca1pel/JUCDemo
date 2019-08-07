package com.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author lhtao
 * 集合类不安全问题
 * ArrayList, HastSet, HashMap
 * @date 2019-04-22 17:31
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args){

        //ConcurrentHashMap利用分段锁保证线程安全
        Map<String, String> map = new ConcurrentHashMap();//Collections.synchronizedMap(new HashMap<>());//new HashMap<>();

        for (int i=1; i<=30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }





    public static void setNotSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();//Collections.synchronizedSet(new HashSet<>());//new HashSet<>();

        for (int i=1; i<=30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }

        //HashSet底层时HashMap。
        //HashMap的值需要2个，而HashSet只需要传1个值原因： HashMap的value有默认值PRESENT = new Object();
        //HashSet传的值其实是HashMap k-v键值对中的key
        new HashSet<>().add("a");
    }


    public static void listNotSafe() {
        //List<String> list = new Vector<>();//new ArrayList<>();

        //List<String> list = Collections.synchronizedList(new ArrayList<>());

        List<String> list = new CopyOnWriteArrayList();

        for (int i=1; i<=30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
        //java.util.ConcurrentModificationException 并发修改异常

        /**
         * 1. 故障现象
         *    java.util.ConcurrentModificationException
         *
         * 2. 导致原因
         *      并发争抢修改导致，参考上课花名册签名情况
         *      一个人正在写入，另外一个同学过来抢夺，导致数据不一致异常，并发修改异常。
         *
         * 3. 解决方案
         *      3.1 Vector  可以保证数据一致性，但会导致并发性急剧下降
         *      3.2 Collections.synchronizedList(new ArrayList<>());
         *      3.3 new CopyOnWriteArrayList();
         *
         * 4. 优化建议(同样的错误不犯第2次)
         */}
}

/**
 * 写时复制
 * CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，而是先将当前容器Object[]进行Copy，
 * 复制出一个新的容器Object[] newElements，然后新的容器Object[] newElements里添加元素，添加完元素之后，再将原容器的引用指向
 * 新的容器 setArray(newElements); 这样做的好处是可以对CopyOnWrite容器进行兵法的读，而不需要加锁，因为当前容器不会添加任何元素。
 * 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器
 */

/*public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}*/
