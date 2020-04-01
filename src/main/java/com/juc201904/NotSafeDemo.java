package com.juc201904;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 故障现象
 * java.util.ConcurrentModificationException
 *
 * 导致原因
 * ArrayList, Set, Map线程不安全
 *
 * 解决方法
 * ① new Vector<>()
 * ② Collections.synchronizedList(new ArrayList<></>())
 * ③ new CopyOnWriteArrayList<></>()
 *
 * @author lhtao
 * @date 2020/4/1 17:37
 */
public class NotSafeDemo {

    public static void main(String[] args) throws Exception {

        List<String> list = new CopyOnWriteArrayList<>(); //Collections.synchronizedList(new ArrayList<>(10)); //new Vector<>(); //new ArrayList<>(10);
        for (int i=1; i<=3; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
