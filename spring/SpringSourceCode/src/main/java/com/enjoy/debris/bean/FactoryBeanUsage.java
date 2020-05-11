package com.enjoy.debris.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class FactoryBeanUsage implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new CustomerClass();
    }

    @Override
    public Class<?> getObjectType() {
        return CustomerClass.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }


    class CustomerClass{

    }
}
