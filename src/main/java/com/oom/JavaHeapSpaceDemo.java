package com.oom;

import java.util.Random;

/**
 * @author lhtao
 * @date 2019-05-09 14:00
 */
public class JavaHeapSpaceDemo {

    public static void main(String[] args){

        String str = "lht";

        /*while(true)
        {
            str += str + new Random().nextInt(111111111) + new Random().nextInt(222222222);
            str.intern();
        }*/

        byte[] bytes = new byte[10 * 1024 * 1024];

        //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

        //str += str + new Random().nextInt(111111111) + new Random().nextInt(222222222);
        //System.out.println(str);
    }
}
