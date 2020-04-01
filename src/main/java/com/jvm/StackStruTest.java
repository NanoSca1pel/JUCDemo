package com.jvm;

/**
 * @author lhtao
 * @date 2020/3/12 10:15
 */
public class StackStruTest {

    public static void main(String[] args) throws Exception {
        //int i = 2 + 3;

        int i = 2;
        int j = 3;
        int k = i + j;
        System.out.println("hello");
        Thread.sleep(10000);

        //System.exit(1);
        //Runtime.getRuntime().halt(1);
        Runtime.getRuntime().exit(1);
        System.out.println("hello");
    }
}
