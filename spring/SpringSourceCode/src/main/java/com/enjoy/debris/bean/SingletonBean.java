package com.enjoy.debris.bean;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// cglib抽象类也可以实例化
@Component
public abstract class SingletonBean {

    public void sayHello(){
        // 每次都获取不同对象实例
        PrototypBean prototypBean =methodInject();
        System.out.println("sayHello: "+ prototypBean.hashCode());

    }

    @Lookup
    protected abstract PrototypBean methodInject();

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    class PrototypBean {}
}


