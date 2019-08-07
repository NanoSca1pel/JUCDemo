package com.gc;

/**
 * @author lhtao
 *
 * jps查看后台进程
 * jinfo查看java运行的各种信息
 * @date 2019-05-05 17:11
 */
public class HelloGC {

    public static void main(String[] args) throws Exception {

        byte[] bytes = new byte[50 * 1024 * 1024];

        System.out.println("*******HelloGC");
        //Thread.sleep(Integer.MAX_VALUE);

        //返回java虚拟机中的内存总量
        //long totalMemory = Runtime.getRuntime().totalMemory();

        //返回java虚拟机试图使用的最大内存量
        //long maxMemory = Runtime.getRuntime().maxMemory();

        //System.out.println("TOTAL_MEMORY(-Xms) = " + totalMemory + "（字节）、" + (totalMemory / (double)1024 / 1024) + "MB");
        //System.out.println("MAX_MEMORY(-Xmx) = " + maxMemory + "（字节）、" + (maxMemory / (double)1024 / 1024) + "MB");
    }
}
