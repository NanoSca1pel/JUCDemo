package com.ref;

/**
 * @author lhtao
 * @date 2019-05-08 18:05
 */
public class StrongReferenceDemo {

    public static void main(String[] args){

        Object obj1 = new Object(); //这样定义的默认就是强引用
        Object obj2 = obj1; //obj2引用赋值
        obj1 = null; //obj1置空。 只有置空的引用才会被gc回收
        System.gc();
        System.out.println(obj2);
    }
}
