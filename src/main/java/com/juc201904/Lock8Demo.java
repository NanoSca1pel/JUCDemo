package com.juc201904;

import java.util.concurrent.TimeUnit;

/**
 *
 * 1.标准访问，先打印邮件还是短信     先邮件后短信
 * 2.暂停4秒钟在邮件方法，先打印邮件还是短信      先邮件后短信
 * 3.新增普通sayHello方法，请问先打印邮件还是hello     先hello后邮件
 * 4.两部手机，先打印邮件还是短信     先短信后邮件
 * 5.两个静态同步方法，同一部手机，先打印邮件还是短信  先邮件后短信
 * 6.两个静态同步方法，2部手机，先打印邮件还是短信    先邮件后短信
 * 7.1个静态同步方法，1个普通方法，同一部手机，先打印邮件还是短信    先短信后邮件
 * 8.1个静态同步方法，1个普通同步方法，2部手机，先打印邮件还是短信   先短信后邮件
 *
 *
 *
 * 知识点
 * ① 一个对象里面如果有多个synchronized方法，某一个时刻内，只要有一个线程去调用其中的一个synchronized方法，
 *    其它线程都只能等待。换句话来说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
 *
 *    synchronized锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它synchronized方法中去
 *
 * ② 普通方法与同步锁无关 普通方法不会和同步方法争抢同一个资源（ps: 同步方法占用了手机，而普通方法只是使用了手机充电器）
 *
 * ③ 使用两个对象后，同步锁争抢的不是同一个资源。
 *
 * ④ synchronized实现同步的基础：Java中的每一个对象都可以作为锁
 *    具体表现为以下3种形式
 *    ⒈ 对于普通方法，锁是当前实例对象
 *    ⒉ 对于同步方法块，锁的是synchronized括号里配置的对象
 *    ⒊ 对于静态同步方法，锁是当前类的class对象
 *
 * ⑤ 当一个线程试图访问同步代码块时，他首先必须得到锁，退出或抛出异常时必须释放锁
 *    也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其它非静态同步方法必须等待获取锁的方法释放锁后才能获取锁。
 *    可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
 *    所以无须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们的锁
 *
 * ⑥ 所有的静态同步方法用的是同一把锁 ———— 类对象本身
 *    这两把锁是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞争关系的。即非静态同步方法无须等待静态同步方法释放锁。
 *    但是一旦一个静态同步方法获取锁后，其它的静态同步方法都必须等待该方法释放锁后才能获取锁。
 *    因为无论其他的静态同步方法和获取锁的静态同步方法是否属于同一个实例对象，它们都属于同一个类的类实例对象
 *
 *
 * 静态同步是全局锁，锁的是整个类。普通同步是同步锁，锁的是当前对象
 *
 * @author lhtao
 * @date 2020/4/2 10:51
 */
public class Lock8Demo {

    public static void main(String[] args) throws Exception {

        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        TimeUnit.MILLISECONDS.sleep(100);

        new Thread(() -> {
            try {
                //phone.sendSMS();
                //phone.sayHello();
                phone2.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

}

class Phone {

    public static synchronized void sendEmail() throws Exception {
        TimeUnit.SECONDS.sleep(4);
        System.out.println("*******sendEmail");
    }

    public synchronized void sendSMS() throws Exception {
        System.out.println("*******sendSMS");
    }

    public void sayHello() throws Exception {
        System.out.println("************sayHello");
    }
}
