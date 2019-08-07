package com.oom;

/**
 * @author lhtao
 * 栈空间默认大小是512k~1024k
 * @date 2019-05-09 13:58
 */
public class StackOverflowErrorDemo {

    public static void main(String[] args){

        stackOverflowError();
    }

    private static void stackOverflowError() {
        stackOverflowError();  //Exception in thread "main" java.lang.StackOverflowError
    }
}
