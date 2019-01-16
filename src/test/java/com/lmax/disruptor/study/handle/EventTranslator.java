package com.lmax.disruptor.study.handle;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.study.event.ValueEvent;

public class EventTranslator implements EventTranslatorOneArg<ValueEvent,String> {

    @Override
    public void translateTo(ValueEvent event, long sequence, String arg0) {
        event.setValue(sequence+":"+arg0);
    }

}
