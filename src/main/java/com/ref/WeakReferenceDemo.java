package com.ref;

import java.lang.ref.WeakReference;

/**
 * @author lhtao
 * @date 2019-05-08 18:33
 */
public class WeakReferenceDemo {

    public static void main(String[] args){

        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);

        System.out.println(o1);
        System.out.println(weakReference.get());

        System.out.println("==========");

        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(weakReference.get());
    }
}
