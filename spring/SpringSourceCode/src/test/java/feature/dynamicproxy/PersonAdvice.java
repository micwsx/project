package feature.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;

public class PersonAdvice implements InvocationHandler {

    private Object targetedObject;

    public PersonAdvice(Object targetedObject) {
        this.targetedObject = targetedObject;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("-------Wash hands.-------");
        BigDecimal total=(BigDecimal)method.invoke(this.targetedObject,objects);
        System.out.println("-------Preparing to pay: $"+total.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"-------");
        return total;
    }

    public Object createPersonProxy(){
        return Proxy.newProxyInstance(targetedObject.getClass().getClassLoader(),
                targetedObject.getClass().getInterfaces(),
                new PersonAdvice(targetedObject));
    }

    public static void main(String[] args) {
        PersonAdvice advice=new PersonAdvice(new Staff("Michael"));
        Person proxyObj=(Person) advice.createPersonProxy();
       // Person personAdvice=(Person)PersonAdvice.createPersonProxy(new Staff("Michael"));
        BigDecimal total=proxyObj.orderFood("Sandwich");
    }

}
