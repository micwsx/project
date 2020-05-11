package com.enjoy.debris.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestTransaction {

    @Transactional
    public void print(){
        System.out.println("TestTransaction.print()");
    }
}
