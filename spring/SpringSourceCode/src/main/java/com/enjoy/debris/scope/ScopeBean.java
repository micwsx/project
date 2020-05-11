package com.enjoy.debris.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("Ape")
public class ScopeBean {

    public ScopeBean() {
        System.out.println("ScopeBean()");
    }

    public void hi(){
        System.out.println("ScopeBean.hi()");
    }
}
