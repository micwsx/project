package com.enjoy.debris.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class Ape implements Scope {

    private ThreadLocal<Object> threadLocal=new ThreadLocal<>();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {

        if (threadLocal.get()!=null){
            return  threadLocal.get();
        }else{
            Object bean=objectFactory.getObject();
            threadLocal.set(bean);
            return bean;
        }
    }

    @Override
    public Object remove(String name) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
