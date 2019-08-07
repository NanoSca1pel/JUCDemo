package com.juc; /**
 * @author lhtao
 * CAS是什么
 * @date 2019-04-22 15:03
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. CAS是什么？ ==> compareAndSet
 *      比较并交换
 */
public class CASDemo {

    public static void main(String[] args){

        AtomicInteger atomicInteger = new AtomicInteger(5);

        //main do thing...

        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current atomicInteger value: " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current atomicInteger value: " + atomicInteger.get());
    }
}
