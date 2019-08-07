package com.gc;

/**
 * @author lhtao
 * @date 2019-05-05 16:20
 *
 * 1.  虚拟机栈（栈帧中的局部变量区，也叫做局部变量表）中引用的对象。
 * 2.  方法区中的类静态属性引用的对象
 * 3.  方法区中常量引用的对象
 * 4. 本地方法栈中JNI（Native方法）引用的对象
 */
public class GCRootDemo {

    private byte[] byteArray = new byte[100 * 1024 * 1024];

    //2.
    //private static GCRootDemo2 t2;

    //3.
    //private static final GCRootDemo3 t3 = new GCRootDemo3(8);

    //4.
    //类似线程中的start()方法


    public static void m1(){

        //1.
        GCRootDemo t1 = new GCRootDemo();
        System.gc();
        System.out.println("第一次GC完成");
    }

    public static void main(String[] args){
        m1();
    }
}
