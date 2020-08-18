package com.research.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class MultiEventNotifier {

    private String notificationAddress;

    public MultiEventNotifier(@Value("MultiEventNotifier@monitor.com") String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    @EventListener({BlackListEvent.class, ContextRefreshedEvent.class})
    public void processEvent() {
        System.out.println("MultiEventNotifier.processEvent()");
    }
}
