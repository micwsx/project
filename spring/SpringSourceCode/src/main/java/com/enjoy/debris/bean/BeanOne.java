package com.enjoy.debris.bean;


public class BeanOne {

    private BeanTwo beanTwo;

    public BeanOne(){}

    public BeanOne(BeanTwo beanTwo){
        this.beanTwo=beanTwo;
    }

    public BeanTwo getBeanTwo() {
        return beanTwo;
    }
}


