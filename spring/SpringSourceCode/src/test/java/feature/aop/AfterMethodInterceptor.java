package feature.aop;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AfterMethodInterceptor implements MethodInterceptor {

    protected final Class<?> targetClass;
    protected final Method method;
    protected final Object[] arguments;

    /**
     *
     * @param targetClass 方法执行类对象
     * @param method    方法对象
     * @param arguments 方法参数
     */
    public AfterMethodInterceptor(Class<?> targetClass, Method method, Object[] arguments) {
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
    }

    private Object invokeAfterMethod() throws Throwable {
        return this.method.invoke(this.targetClass.newInstance(),this.arguments);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } finally {
            invokeAfterMethod();
        }
    }
}
