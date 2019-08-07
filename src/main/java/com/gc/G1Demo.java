package com.gc;

import java.util.Random;

/**
 * @author lhtao
 *
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+UseG1GC
 *
 * @date 2019-05-11 12:50
 */
public class G1Demo {

    public static void main(String[] args){

        String str = "lht";

        while(true) {

            str += str + new Random().nextInt(7777777) + new Random().nextInt(8888888);
            str.intern();
        }
    }
}
