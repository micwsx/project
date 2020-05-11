package feature.aop;

import feature.dynamicproxy.Person;
import feature.dynamicproxy.Staff;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersonAdvice personAdvice=new PersonAdvice(new Staff("Michael"));
        Person proxyObj=(Person)personAdvice.createProxyObject();
        proxyObj.orderFood("Pizza");
    }
}
