package com.lmax.disruptor.study.handle;

import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.study.event.ValueEvent;

public class FirstEventHandler  implements EventHandler<ValueEvent> {

    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("----------first event handler:" + event.getValue() +",endOfBatch " + endOfBatch );
    }

}
