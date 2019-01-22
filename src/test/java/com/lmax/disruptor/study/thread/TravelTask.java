package com.lmax.disruptor.study.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**
 * 旅行线程
 * @author PENGZHIGANG
 *
 */
public class TravelTask implements Runnable {

    private CyclicBarrier barrier;
    private String name;
    private int arriveTime;// 赶到的时间

    public TravelTask(CyclicBarrier barrier, String name, int arriveTime) {
        this.barrier = barrier;
        this.name = name;
        this.arriveTime = arriveTime;
    }

    @Override
    public void run() {
        try {
            // 模拟达到需要花的时间
            Thread.sleep(arriveTime * 1000);
            System.out.println(name + "到达集合点");
            barrier.await();
            System.out.println(name + "开始旅行啦～～");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
