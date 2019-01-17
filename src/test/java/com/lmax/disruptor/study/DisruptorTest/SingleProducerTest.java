package com.lmax.disruptor.study.DisruptorTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.study.event.ValueEvent;
import com.lmax.disruptor.study.singleproducer.FirstEventHandler;
import com.lmax.disruptor.study.singleproducer.FourthEventHandler;
import com.lmax.disruptor.study.singleproducer.LastEventHandler;
import com.lmax.disruptor.study.singleproducer.SecondEventHandler;
import com.lmax.disruptor.study.singleproducer.ThirdEventHandler;

public class SingleProducerTest {
    
    private static final int BUFFER_SIZE = 2;
    
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
        
          /**
           * 在没有任何一个先后顺序的时候
           *  每一个事件处理者 维护自己的一个序列
           *  每一个事件处理者将自己的序列跟发布者的游标进行比较 如果大于游标将进行等待
           *  
           *  如果事件生产者将生产的进行覆盖消费者，将获取最小的事件处理序列将进行比较
           *  
           */
//         disruptor.handleEventsWith(firstEventHandler/*, secondHandler, thirdEventHandler, fourthEventHandler, lastEventHandler*/);
        disruptor.handleEventsWith(firstEventHandler);
        
        // 有一个先后的执行顺序
        /**
         * 1、先预启动事件处理者
         * 
         * 2、after的方法的作用是返回一个事件处理组 含有上一个事件处理者的序列
         *   设置一个屏障，并将标识是否结束链有true改为false表示并 没有结束该链
         */
        disruptor.after(firstEventHandler).handleEventsWith(secondHandler);
        
        // 在处理者firstEventHandler之后执行secondHandler最后执行lastEventHandler
        disruptor.after(firstEventHandler).handleEventsWith(secondHandler).then(lastEventHandler);

        // 启动爆裂者
        disruptor.start();
        RingBuffer<ValueEvent> ringBuffer = disruptor.getRingBuffer();
        
        // 发布事件
        for (int i = 0; i < 3; i++) {
            long sequence = ringBuffer.next();
            ValueEvent valueEvent = disruptor.get(sequence);
            valueEvent.setValue(String.valueOf(i));
            System.out.println("发布事件：" + i);
            ringBuffer.publish(sequence);
        }
        
        // 停止
        disruptor.shutdown();
    }
}
