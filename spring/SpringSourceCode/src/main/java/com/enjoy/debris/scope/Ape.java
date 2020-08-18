package com.enjoy.debris.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.Map;

public class Ape implements Scope {

    private Map<String, ThreadLocal<Object>> threadLocalMap = new HashMap();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if (threadLocalMap.get(name) != null) {
            return threadLocalMap.get(name).get();
        } else {
            Object bean = objectFactory.getObject();
            ThreadLocal<Object> obj = new ThreadLocal<>();
            obj.set(objectFactory.getObject());
            threadLocalMap.put(name, obj);
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
