package com.lmax.disruptor.study.thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        /**
         * public CyclicBarrier(int parties)
         * 第一个参数，表示那个一起执行的线程个数。
         * 
         * public CyclicBarrier(int parties, Runnable barrierAction)
         * 第二个参数，表示线程都处于barrier时，一起执行之前，先执行的一个线程。
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new TourGuideTask());
        Executor executor = Executors.newFixedThreadPool(3);
        // 登哥最大牌，到的最晚
        executor.execute(new TravelTask(cyclicBarrier, "哈登", 5));
        executor.execute(new TravelTask(cyclicBarrier, "保罗", 3));
        executor.execute(new TravelTask(cyclicBarrier, "戈登", 1));
    }
}
