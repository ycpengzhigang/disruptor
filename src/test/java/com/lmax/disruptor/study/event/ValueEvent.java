package com.lmax.disruptor.study.event;

import com.lmax.disruptor.EventFactory;
/**
 * 事件
 * @author PENGZHIGANG
 *
 */
public class ValueEvent {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public final static EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>() {
        public ValueEvent newInstance() {
            return new ValueEvent();
        }
    };
    
}
