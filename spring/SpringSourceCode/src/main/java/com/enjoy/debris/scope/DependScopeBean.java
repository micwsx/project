package com.enjoy.debris.scope;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class DependScopeBean implements BeanNameAware {

    @Autowired
    private ScopeBean scopeBean;


    public ScopeBean getScopeBean() {
        return scopeBean;
    }

    @Override
    public void setBeanName(String s) {

    }

}
