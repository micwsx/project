package feature.aop;

import java.lang.reflect.Method;

public class AfterThrowingMethodInterceptor implements MethodInterceptor {

    protected final Class<?> targetClass;
    protected final Method method;
    protected final Object[] arguments;


    public AfterThrowingMethodInterceptor(Class<?> targetClass, Method method, Object[] arguments) {
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
    }

    private Object invokeAfterThrowingMethod()throws Throwable{
        return this.method.invoke(this.targetClass.newInstance(), this.arguments);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
          return  methodInvocation.proceed();
        } catch (Throwable throwable) {
           invokeAfterThrowingMethod();
           throw throwable;
        }
    }
}
