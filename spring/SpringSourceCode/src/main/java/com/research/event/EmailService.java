package com.research.event;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;
    private List<String> blackList;

    public EmailService() {
        blackList = new ArrayList<>();
        blackList.add("Michael.wu@computop.com");
        blackList.add("Jack@computop.com");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // 如果目标地址在黑名单中，则触发通知BlackListEvent事件
    public void sendEmail(String address, String content) {
        if (blackList.contains(address)) {
            System.out.println("high risk exits with this email address.");
            applicationEventPublisher.publishEvent(new BlackListEvent(this, address, content));
            return;
        } else {
            System.out.println("the normal email address.");
        }
    }
}
