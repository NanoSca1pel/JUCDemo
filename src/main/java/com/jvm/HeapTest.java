package com.jvm;

import java.util.ArrayList;

/**
 * @author lhtao
 * @date 2020/3/16 15:17
 */
public class HeapTest {

    public static void main(String[] args) throws Exception {

        ArrayList<HeapTest> heapList = new ArrayList<>();
        while (true) {
            heapList.add(new HeapTest());
            Thread.sleep(1);
        }
    }
}
