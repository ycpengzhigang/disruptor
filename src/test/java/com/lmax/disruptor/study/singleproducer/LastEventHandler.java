package com.lmax.disruptor.study.singleproducer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.study.event.ValueEvent;

public class LastEventHandler  implements EventHandler<ValueEvent> {

    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("----------last event handler:" + event.getValue() +",endOfBatch " + endOfBatch );
    }

}
