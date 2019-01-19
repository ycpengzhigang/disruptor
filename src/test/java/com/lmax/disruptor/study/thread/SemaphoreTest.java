package com.lmax.disruptor.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量测试
 * 
 * @author PENGZHIGANG
 *
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 信号量
        final Semaphore semaphore = new Semaphore(5);

        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int no = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 使用acquire()获取锁
                        semaphore.acquire();
                        System.out.println("Accessing: " + no);
                        // 睡眠1秒
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    } finally {
                        // 使用完成释放锁
                        semaphore.release();
                    }
                }
            };
            
            exec.execute(run);
        }

    }
}
