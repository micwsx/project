package feature.aop;

import java.lang.reflect.Method;

public class AfterReturningMethodInterceptor implements MethodInterceptor{

    protected final Class<?> targetClass;
    protected final Method method;
    protected final Object[] arguments;


    public AfterReturningMethodInterceptor(Class<?> targetClass, Method method, Object[] arguments) {
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
    }

    private Object invokeAfterReturningMethod()throws Throwable{
        return this.method.invoke(this.targetClass.newInstance(), this.arguments);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result=methodInvocation.proceed();
        invokeAfterReturningMethod();
        return result;
    }
}
