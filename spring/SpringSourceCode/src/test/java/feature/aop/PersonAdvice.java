package feature.aop;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class PersonAdvice implements InvocationHandler {

    private final Object target;

    public PersonAdvice(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Class<?> clazz = EnhanceAdvice.class;
        Method beforeMethod = clazz.getMethod("before");
        Method afterMethod = clazz.getMethod("after");
        Method afterReturningMethod = clazz.getMethod("afterReturning");
        Method afterThrowingMethod = clazz.getMethod("afterThrowing");
        Method aroundMethod = clazz.getMethod("around", ProceedingJoinPoint.class);

        AfterMethodInterceptor afterMethodInterceptor = new AfterMethodInterceptor(clazz, afterMethod, new Object[0]);
        BeforeMethodInterceptor beforeMethodInterceptor = new BeforeMethodInterceptor(clazz, beforeMethod, new Object[0]);
        AfterMethodInterceptor afterThrowingMethodInterceptor = new AfterMethodInterceptor(clazz, afterThrowingMethod, new Object[0]);
        AfterReturningMethodInterceptor afterReturningMethodInterceptor = new AfterReturningMethodInterceptor(clazz, afterReturningMethod, new Object[0]);
        AroundMethodInterceptor aroundMethodInterceptor=new AroundMethodInterceptor(clazz,aroundMethod);
        // 添加顺序有关系
        List<MethodInterceptor> chain = new ArrayList<>();
//        chain.add(afterThrowingMethodInterceptor);
//        chain.add(afterReturningMethodInterceptor);
//        chain.add(afterMethodInterceptor);
        chain.add(beforeMethodInterceptor);
        chain.add(aroundMethodInterceptor);

        MethodInvocation methodInvocation = new ReflectiveMethodInvoation(o, this.target, this.target.getClass(), method, objects, chain);
        return methodInvocation.proceed();
    }

    public Object createProxyObject() {
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),
                this.target.getClass().getInterfaces(),
                this);
    }
}
