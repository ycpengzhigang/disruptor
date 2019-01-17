package com.lmax.disruptor.study.DisruptorTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.study.multiproducer.Consumer;
import com.lmax.disruptor.study.multiproducer.Order;
import com.lmax.disruptor.study.multiproducer.Producer;

public class MultiProducerTest {

    private static final int BUFFER_SIZE = 2;

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory factory = Executors.defaultThreadFactory();

        // 创建多个生产者
        Disruptor<Order> disruptor = new Disruptor<Order>(new EventFactory<Order>() {

            @Override
            public Order newInstance() {
                return new Order();
            }
        }, BUFFER_SIZE, factory, ProducerType.MULTI, new BlockingWaitStrategy());

        RingBuffer<Order> ringBuffer = disruptor.getRingBuffer();

        SequenceBarrier barrier = ringBuffer.newBarrier();

        Consumer[] consumers = new Consumer[3];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("consumer:" + i);
        }

        // 创建消费工作 池
        WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer, barrier, new IntEventExceptionHandler(),
                consumers);

        // 增加追踪序列
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        // 启动
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())); 
        
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        
        for (int i = 0; i < 10; i++) {
            final Producer p = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (int j = 0; j < 100; j++) {
                        p.onData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }
        
        Thread.sleep(5000);
        System.out.println("------------开始生产-------------");
        countDownLatch.countDown();
        Thread.sleep(1);
        System.out.println("总数："+consumers[0].getCount());
    }

    // 异常处理
    static class IntEventExceptionHandler implements ExceptionHandler {
        @Override
        public void handleEventException(Throwable arg0, long arg1, Object arg2) {
        }

        @Override
        public void handleOnShutdownException(Throwable arg0) {
        }

        @Override
        public void handleOnStartException(Throwable arg0) {
        }
    }

}
