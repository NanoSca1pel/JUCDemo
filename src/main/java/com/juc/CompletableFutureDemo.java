package com.juc;

import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 *
 * 没有指定Executor的方法会使用ForkJoinPool.commonPool() 作为它的线程池执行异步代码。如果指定线程池，则使用指定的线程池运行。以下所有的方法都类同。
 *
 * runAsync方法不支持返回值。
 * supplyAsync可以支持返回值。
 *
 * 计算结果完成时或抛出异常的回调方法
 * whenComplete 和 whenCompleteAsync 的区别：
 * whenComplete：是执行当前任务的线程执行继续执行 whenComplete 的任务。
 * whenCompleteAsync：是执行把 whenCompleteAsync 这个任务继续提交给线程池来进行执行。
 * exceptionally: 异常时执行
 *
 * thenApply 方法
 * Function<? super T,? extends U>
 * T：上一个任务返回结果的类型
 * U：当前任务的返回值类型
 * 当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化。
 *
 * handle 方法
 * handle 是执行任务完成时对结果的处理。
 * handle 方法和 thenApply 方法处理方式基本一样。不同的是 handle 是在任务完成后再执行，还可以处理异常的任务。thenApply 只可以执行正常的任务，任务出现异常则不执行 thenApply 方法。
 * 在 handle 中可以根据任务是否有异常来进行做相应的后续处理操作。而 thenApply 方法，如果上个任务出现错误，则不会执行 thenApply 方法。
 *
 * thenAccept 消费处理结果
 * 接收任务的处理结果，并消费处理，无返回结果。
 * 该方法只是消费执行完成的任务，并可以根据上面的任务返回的结果进行处理。并没有后续的输错操作。
 * 该方法同 thenAccept 方法类似。不同的是上个任务处理完成后，并不会把计算的结果传给 thenRun 方法。只是处理玩任务后，执行 thenAccept 的后续操作。
 *
 * thenRun 方法
 * 跟 thenAccept 方法不一样的是，不关心任务的处理结果。只要上面的任务执行完成，就开始执行 thenAccept 。
 *
 * thenCombine 合并任务
 * thenCombine 会把 两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
 *
 * thenAcceptBoth
 * 当两个CompletionStage都执行完成后，把结果一块交给thenAcceptBoth来进行消耗
 *
 * applyToEither 方法
 * 两个CompletionStage，谁执行返回的结果快，我就用那个CompletionStage的结果进行下一步的转化操作。
 *
 * acceptEither 方法
 * 两个CompletionStage，谁执行返回的结果快，我就用那个CompletionStage的结果进行下一步的消耗操作。
 *
 * runAfterEither 方法
 * 两个CompletionStage，任何一个完成了都会执行下一步的操作（Runnable）
 *
 * runAfterBoth
 * 两个CompletionStage，都完成了计算才会执行下一步的操作（Runnable）
 *
 * thenCompose 方法
 * thenCompose 方法允许你对两个 CompletionStage 进行流水线操作，第一个操作完成时，将其结果作为参数传递给第二个操作。
 *
 *
 * @author lhtao
 * @descript
 * @date 2021/6/18 13:17
 */
public class CompletableFutureDemo {

    public static void printTimeAndThread(String tag) {
        String result = new StringJoiner("\t|\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(tag)
                .toString();
        System.out.println(result);
    }

    /*public static void main(String[] args) throws Exception {
        printTimeAndThread("李杰进入餐厅");
        printTimeAndThread("李杰点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()-> {
            printTimeAndThread("厨师炒菜");
            try { TimeUnit.MILLISECONDS.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
            printTimeAndThread("厨师打饭");
            try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
            return "番茄炒蛋 + 米饭 做好了";
        });

        printTimeAndThread("李杰在打王者");
        printTimeAndThread(String.format("%s, 李杰开吃", cf1.join()));
    }*/

    /*public static void main(String[] args) throws Exception {
        printTimeAndThread("李杰进入餐厅");
        printTimeAndThread("李杰点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()-> {
            printTimeAndThread("厨师炒菜");
            try { TimeUnit.MILLISECONDS.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
            return "番茄炒蛋做好了";
        }).thenCompose(dish -> CompletableFuture.supplyAsync(()-> {  //dish代表上一个CompletableFuture的返回结果
            printTimeAndThread("服务员打饭");
            try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
            return dish + ", 米饭打好了";
        }));

        printTimeAndThread("李杰在打王者");
        printTimeAndThread(String.format("%s, 李杰开吃", cf1.join()));
    }*/

    /*public static void main(String[] args) throws Exception {
        printTimeAndThread("李杰进入餐厅");
        printTimeAndThread("李杰点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()-> {
            printTimeAndThread("厨师炒菜");
            try { TimeUnit.MILLISECONDS.sleep(300); } catch(InterruptedException e) { e.printStackTrace(); }
            return "番茄炒蛋";
        }).thenComposeAsync(dish -> {
            printTimeAndThread("服务员A 准备大范，但是被领导叫走了，打饭交给服务员B");

            return CompletableFuture.supplyAsync(()-> {
               printTimeAndThread("服务员B 打饭");
               try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
               return dish + " + 米饭 做好了";
            });
        });

        printTimeAndThread("李杰在打王者");
        printTimeAndThread(String.format("%s, 李杰开吃", cf1.join()));
    }*/


    /*public static void main(String[] args) throws Exception {
        printTimeAndThread("李杰进入餐厅");
        printTimeAndThread("李杰点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()-> {
            printTimeAndThread("厨师炒菜");
            try { TimeUnit.MILLISECONDS.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
            return "番茄炒蛋做好了";
        }).thenCombine(CompletableFuture.supplyAsync(()-> {  //dish代表上一个CompletableFuture的返回结果
            printTimeAndThread("服务员蒸饭");
            try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
            return "米饭蒸好了";
        }), (dish, rice) ->{
            printTimeAndThread("服务员打饭");
            try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
            return String.format("%s + %s ", dish, rice);
        });

        printTimeAndThread("李杰在打王者");
        printTimeAndThread(String.format("%s, 李杰开吃", cf1.join()));
    }*/

    /*public static void main(String[] args) throws Exception {
        printTimeAndThread("李杰进入餐厅");
        printTimeAndThread("李杰点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()-> {
            printTimeAndThread("厨师炒菜");
            try { TimeUnit.MILLISECONDS.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
            return "番茄炒蛋做好了";
        }).thenCombine(CompletableFuture.supplyAsync(()-> {  //dish代表上一个CompletableFuture的返回结果
            printTimeAndThread("服务员蒸饭");
            try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
            return "米饭蒸好了";
        }), (dish, rice) ->{
            printTimeAndThread("服务员打饭");
            try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
            return String.format("%s + %s ", dish, rice);
        });

        printTimeAndThread("李杰在打王者");
        printTimeAndThread(String.format("%s, 李杰开吃", cf1.join()));
    }*/

    /*public static void main(String[] args) throws Exception {
        printTimeAndThread("李杰吃好了");
        printTimeAndThread("李杰结账，要求开发票");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()-> {
            printTimeAndThread("服务员收款500");
            try { TimeUnit.MILLISECONDS.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
            return "500";
        }).thenApplyAsync(money -> {
            printTimeAndThread(String.format( "服务员开发票 面额%s元", money));
            try { TimeUnit.MILLISECONDS.sleep(100); } catch(InterruptedException e) { e.printStackTrace(); }
            return String.format("%s元发票", money);
        });

        printTimeAndThread("李杰接到朋友的电话，想一起打游戏");
        printTimeAndThread(String.format("小白拿到%s, 准备回家", cf1.join()));
    }*/

    /*public static void main(String[] args) throws Exception {
        printTimeAndThread("李杰走出餐厅，来到公交站");
        printTimeAndThread("等待 700路 或者 800路 公交到来");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()-> {
            printTimeAndThread("700路公交正在赶来");
            try { TimeUnit.MILLISECONDS.sleep(200); } catch(InterruptedException e) { e.printStackTrace(); }
            return "700路到了";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("800路公交正在赶来");
            try { TimeUnit.MILLISECONDS.sleep(300); } catch(InterruptedException e) { e.printStackTrace(); }
            return "800路到了";
        }), firstComeBus -> {
            printTimeAndThread(firstComeBus);
            if (firstComeBus.startsWith("700")) {
                throw new RuntimeException(firstComeBus + "撞树了...");
            }
            return firstComeBus;
        }).exceptionally(e ->{
            printTimeAndThread(e.getMessage());
            printTimeAndThread("李杰叫出租车了");
            return "出租车叫到了";
        });

        printTimeAndThread(String.format("%s, 李杰坐车回家", cf1.join()));
    }*/


    /*public static void main(String[] args) throws Exception {
        System.out.println(Runtime.getRuntime().availableProcessors());

        //查看当前线程数
        System.out.println(ForkJoinPool.commonPool().getPoolSize());

        //查看最大线程数线程数
        System.out.println(Runtime.getRuntime().availableProcessors());
    }*/

    /*public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        printTimeAndThread("李杰和小伙伴们 进入餐厅点菜");
        long startTime = System.currentTimeMillis();

        CompletableFuture[] dishes = IntStream.rangeClosed(1, 200)
                .mapToObj(i -> new Dish("菜" + i, 1))
                .map(dish -> CompletableFuture.runAsync(dish::make, threadPool))
                .toArray(size -> new CompletableFuture[size]);

        CompletableFuture.allOf(dishes).join();

        threadPool.shutdown();
        
        printTimeAndThread("菜都做好了,上桌 " + (System.currentTimeMillis() - startTime));

    }*/

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = CompletableFuture.completedFuture("hello!")
                .thenApply(s -> s + "world!");
        String s1 = future.get();
        System.out.println(s1);
        // 这次调用将被忽略。
        future.thenApply(s -> s + "nice!");
        String s2 = future.get();
        System.out.println(s2);
        //future中断后除非使用新对象接收，否则所有后续调用都会被忽略
        CompletableFuture<String> future2 = future.thenApply(s -> s + "nice!");
        System.out.println(future2.get());

        CompletableFuture<String> future3 = CompletableFuture.completedFuture("hello!")
                .thenApply(s -> s + "world!").thenApply(s -> s + "nice!");
        System.out.println(future3.get());
    }
}

@Data
@AllArgsConstructor
class Dish {

    private String name;

    private Integer productTime;

    public void make() {
        CompletableFutureDemo.printTimeAndThread(this.name + " 制作完毕");
    }
}