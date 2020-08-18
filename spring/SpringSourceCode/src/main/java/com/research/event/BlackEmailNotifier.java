package com.research.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BlackEmailNotifier implements ApplicationListener<BlackListEvent> {

    private String notificationAddress;

    public BlackEmailNotifier(@Value("administrator@monitor.com") String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    @Override
    public void onApplicationEvent(BlackListEvent blackListEvent) {
        System.out.println(this.notificationAddress+" received the BlackListEvent: "+blackListEvent.getAddress()+">>>>"+blackListEvent.getContent());
    }
}
