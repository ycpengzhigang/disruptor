package com.lmax.disruptor.study.handle;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.study.event.ValueEvent;

public class ValueEventHandler  implements EventHandler<ValueEvent> {

    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("----------event handler:" + event.getValue() +",endOfBatch" + endOfBatch );
        
    }

}
