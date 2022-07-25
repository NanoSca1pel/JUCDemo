package com.juc;

import java.util.concurrent.*;

/**
 * @Description: CompletableFuture 尚硅谷周阳版
 * @author: lhtao
 * @date: 2022年06月24日 9:23
 */
public class CompletableFutureDemo2 {

    public static void main(String[] args) throws Exception {

        // main方法中所有的测试情况在不使用自定义线程池的情况下，都会使用自带的默认线程池ForkJoinPool
        ExecutorService threadPool = new ThreadPoolExecutor(1, 80, 1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        try{
            //runAsyncNoThreadPool();

            //runAsyncThreadPool(threadPool);

            //supplyAsyncNoThreadPool();

            //supplyAsyncThreadPool(threadPool);

            //futureBase();

            futureWhenCompleteAndException(threadPool);


            System.out.println(Thread.currentThread().getName() + "线程先去忙其它任务了");

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // 在main方法中，不关闭线程池的情况下，主线程不会结束
            // 如果自定义线程池是static修饰的全局变量，可以不需要关闭，当关闭时其它方法进来使用线程池就会无法使用
            if (!threadPool.isShutdown()) {
                threadPool.shutdown();
            }
        }
    }

    private static void futureWhenCompleteAndException(ExecutorService threadPool) throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "---- come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("------ 1秒钟后出结果：" + result);
            if (result > 5) {
                result = result/0;
            }
            return result;
        }, threadPool).whenComplete((v,e) -> {
            if (e == null) {
                System.out.println("------ 计算完成，更新系统Update：" + v);
            }
        }).exceptionally(e -> {
            System.out.println("------ 异常情况" + e.getCause() + "\t" + e.getMessage());
            return null;
        });
    }

    private static void futureBase() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "---- come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("------ 1秒钟后出结果：" + result);
            return result;
        });
        System.out.println(Thread.currentThread().getName() + "线程先去忙其它任务了");

        System.out.println(future.get());
    }

    // public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
    private static void supplyAsyncThreadPool(ExecutorService threadPool) throws InterruptedException, ExecutionException {
        CompletableFuture<String> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try { TimeUnit.SECONDS.sleep(1); } catch(InterruptedException e) { e.printStackTrace(); }
            return "supplyAsync2";
        }, threadPool);
        System.out.println(supplyAsync2.get());
    }

    // public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
    private static void supplyAsyncNoThreadPool() throws InterruptedException, ExecutionException {
        CompletableFuture<String> supplyAsync1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try { TimeUnit.SECONDS.sleep(1); } catch(InterruptedException e) { e.printStackTrace(); }
            return "supplyAsync1";
        });
        System.out.println(supplyAsync1.get());
    }

    // public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor)
    private static void runAsyncThreadPool(ExecutorService threadPool) throws InterruptedException, ExecutionException {
        CompletableFuture<Void> runAsync2 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try { TimeUnit.SECONDS.sleep(1); } catch(InterruptedException e) { e.printStackTrace(); }
        }, threadPool);
        System.out.println(runAsync2.get());
    }

    // public static CompletableFuture<Void> runAsync(Runnable runnable)
    private static void runAsyncNoThreadPool() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> runAsync1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try { TimeUnit.SECONDS.sleep(1); } catch(InterruptedException e) { e.printStackTrace(); }
        });
        System.out.println(runAsync1.get());
    }
}
