package com.research.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BlackEmailNotifier2 {

    private String notificationAddress;

    public BlackEmailNotifier2(@Value("BlackEmailNotifier2@monitor.com") String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    @EventListener
    public void processEvent(BlackListEvent blackListEvent) {
        System.out.println(this.notificationAddress + " received the BlackListEvent: " + blackListEvent.getAddress() + ">>>>" + blackListEvent.getContent());
    }
}
