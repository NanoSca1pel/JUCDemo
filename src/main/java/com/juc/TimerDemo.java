package com.juc;

import cn.hutool.core.date.DateUtil;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: TODO
 * @author: lhtao
 * @date: 2022年05月11日 10:50
 */
public class TimerDemo {
    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread().getName() + ":主线程进入");
        boolean[] isRun = {true};
        Timer timer = new Timer();

        //延迟执行一次
        //timer.schedule(new TimerTask() {
        //    @Override
        //    public void run() {
        //        System.out.println(Thread.currentThread().getName() + ":我是你爸爸");
        //        //isRun[0] = false;
        //        timer.cancel();
        //    }
        //}, 2000);

        //按日期定时执行
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ":我是你爸爸");
                count++;
                if (count == 3) {
                    //isRun[0] = false;
                    timer.cancel();
                }
            }
        }, DateUtil.parse("2022-05-11 13:00:00", "yyyy-MM-dd HH:mm:ss"), 86400000);

        //如果需要主线程阻塞等待定时任务完成，则需要通过状态位判断
        //while (isRun[0]) {
        //
        //}
        System.out.println(Thread.currentThread().getName() + ":主线程关闭");
    }
}
