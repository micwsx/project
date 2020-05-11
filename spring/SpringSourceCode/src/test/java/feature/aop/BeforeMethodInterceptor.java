package feature.aop;

import java.lang.reflect.Method;

public class BeforeMethodInterceptor implements MethodInterceptor {

    protected final Class<?> targetClass;
    protected final Method method;
    protected final Object[] arguments;


    public BeforeMethodInterceptor(Class<?> targetClass, Method method, Object[] arguments) {
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
    }

    private Object invokeBeforeMethod()throws Throwable{
        return this.method.invoke(this.targetClass.newInstance(), this.arguments);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        invokeBeforeMethod();
        return methodInvocation.proceed();
    }
}
