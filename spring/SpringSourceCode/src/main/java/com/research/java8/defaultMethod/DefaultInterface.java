package com.research.java8.defaultMethod;

public class DefaultInterface {
    public static void main(String[] args) {

//        new C().hello();
        new DDDD().hello();
    }
}

class J implements M,A{
    // 指定调用方法
    @Override
    public void hello() {
        M.super.hello();
    }
}

interface M {
    default void hello() {
        System.out.println("Hello from M");
    }
}


interface A {
    default void hello() {
        System.out.println("Hello from A");
    }
}


interface BBBBB extends A{}
interface CCCCC extends A{}
class DDDD implements BBBBB,CCCCC{

}


interface B extends A {
    default void hello() {
        System.out.println("Hello from B");
    }
}

class D implements A {
    @Override
    public void hello() {
        System.out.println("Hello from D");
    }
}


class C extends D implements A, B {

}

