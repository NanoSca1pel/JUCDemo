package com.juc;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description:
 * 案例说明： 电商比价需求，模拟如下情况：
 * 1.需求
 *  1.1 同一款产品，同时搜索出同款产品在各大电商平台的售价；
 *  1.2 同一款产品，同时搜索出本产品在同一电商平台下，各个入驻卖家售价是多少
 *
 * 2.输出： 出来的结果希望是同款产品在不同地方的价格清单列表，返回一个List<String>
 *   ps: 《mysql》 in 京东 price is 88.05
 *       《mysql》 in 当当 price is 86.11
 *       《mysql》 in 淘宝 price is 90.43
 *
 * 3.技术要求
 *  3.1 函数式编程
 *  3.2 链式编程
 *  3.3 stream流式计算
 * @author: lhtao
 * @date: 2022年07月25日 10:47
 */
public class CompletableFutureDemo3 {

    static List<NetMall> list = Arrays.asList(new NetMall("京东"), new NetMall("当当"), new NetMall("淘宝"));

    /**
     * step by step 一家一家查
     * @param list 商家集合
     * @param productName 产品名称
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list
                .stream()
                .map(netMall ->
                        String.format(productName + " in %s is %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    /**
     * 并行查 List<NewMall> -> List<CompletableFuture> -> List<String>
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list
                .stream()
                .map(netMall ->
                        CompletableFuture.supplyAsync(() -> String.format(productName + " in %s is %.2f",
                        netMall.getNetMallName(),
                        netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(cf -> cf.join())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        List<String> result = getPrice(list, "mysql");
        for (String s : result) {
            System.out.println(s);
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start) + "毫秒");

        System.out.println("========================");

        long start2 = System.currentTimeMillis();
        List<String> result2 = getPriceByCompletableFuture(list, "mysql");
        for (String s : result2) {
            System.out.println(s);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("耗时:" + (end2 - start2) + "毫秒");
    }
}


class NetMall {

    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        try { TimeUnit.SECONDS.sleep(1); } catch(InterruptedException e) { e.printStackTrace(); }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}