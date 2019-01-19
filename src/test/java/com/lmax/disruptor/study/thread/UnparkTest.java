package com.lmax.disruptor.study.thread;

import java.util.concurrent.locks.LockSupport;

public class UnparkTest {
    public static void main(String[] args) {
        // LockSupport.unpark 解除线程阻塞
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 等待获取许可
                LockSupport.park();
//                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + " is blocking ");
            }

        });
        thread.start();
        
        // 中断并不会出现终端异常
//        thread.interrupt();
        
        // 唤醒  释放许可
        LockSupport.unpark(thread);
        System.out.println("唤醒线程！");
    }
}
