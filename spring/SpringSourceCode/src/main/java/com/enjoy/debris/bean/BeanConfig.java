package com.enjoy.debris.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
@Component
public class BeanConfig {

    @Bean
    public BeanOne beanOne(){
        return new BeanOne(beanTwo());
    }

    @Bean
    public BeanOne beanOne2(){
        return new BeanOne(beanTwo());
    }

    @Bean
    public BeanTwo beanTwo(){
        return new BeanTwo();
    }

}
