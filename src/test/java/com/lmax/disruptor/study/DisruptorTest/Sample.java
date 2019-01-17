package com.lmax.disruptor.study.DisruptorTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.study.event.ValueEvent;
import com.lmax.disruptor.study.handle.FirstEventHandler;
import com.lmax.disruptor.study.handle.FourthEventHandler;
import com.lmax.disruptor.study.handle.LastEventHandler;
import com.lmax.disruptor.study.handle.SecondEventHandler;
import com.lmax.disruptor.study.handle.ThirdEventHandler;

public class Sample {
    
    private static final int BUFFER_SIZE = 4;
    
    public static void main(String[] args) throws InterruptedException {
        // 线程工厂
        ThreadFactory producerFactory = Executors.defaultThreadFactory();
        
        // 创建Disruptor
        Disruptor<ValueEvent> disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, BUFFER_SIZE, producerFactory,
                ProducerType.SINGLE, new BlockingWaitStrategy());

        // 创建事件处理者
        FirstEventHandler firstEventHandler = new FirstEventHandler();
        SecondEventHandler secondHandler = new SecondEventHandler();
        ThirdEventHandler thirdEventHandler = new ThirdEventHandler();
        FourthEventHandler fourthEventHandler = new FourthEventHandler();
        LastEventHandler lastEventHandler = new LastEventHandler();
        
        // 在没有任何一个先后顺序的时候
        disruptor.handleEventsWith(firstEventHandler, secondHandler, thirdEventHandler, fourthEventHandler, lastEventHandler);
//        disruptor.after(firstEventHandler).handleEventsWith(secondHandler);
        // 在处理者firstEventHandler之后执行secondHandler最后执行lastEventHandler
//        disruptor.after(firstEventHandler).handleEventsWith(secondHandler).then(lastEventHandler);

        // 启动爆裂者
        disruptor.start();
        RingBuffer<ValueEvent> ringBuffer = disruptor.getRingBuffer();
        
        // 发布事件
        for (int i = 0; i < 2; i++) {
            long sequence = ringBuffer.next();
            ValueEvent valueEvent = disruptor.get(sequence);
            valueEvent.setValue(String.valueOf(i));
            System.out.println("发布事件：" + i);
            ringBuffer.publish(sequence);
            TimeUnit.SECONDS.sleep(5);
        }
        
        // 停止
        disruptor.shutdown();
    }
}
