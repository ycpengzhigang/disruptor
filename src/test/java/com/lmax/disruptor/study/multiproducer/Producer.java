package com.lmax.disruptor.study.multiproducer;

import com.lmax.disruptor.RingBuffer;
/**
 * 生产者
 * @author PENGZHIGANG
 *
 */
public class Producer {
    
    /**
     *  视为一个事件队列
     */
    private final RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer){
        this.ringBuffer=ringBuffer;
    }
    
    /**
     *  发布事件
     * @param data
     */
    public void onData(String data){
        // 可以把ringBuffer看作是一个事件队列，那么next就是得到下一个事件槽
        long sequence = ringBuffer.next();
        try {
            Order order = ringBuffer.get(sequence);
            order.setId(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            // 发布事件
            ringBuffer.publish(sequence);
        }
    }
}
