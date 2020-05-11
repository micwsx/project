package feature.aop;

import java.lang.reflect.Method;
import java.util.List;

public class MethodInvocation {

    protected final Object proxy;
    protected final Object target;
    private final Class<?> targetClass;
    protected final Method method;
    protected Object[] arguments;

    protected List<?> interceptorsAndDynamicMethodMatchers;

    private int currentInterceptorIndex = -1;

    /**
     *
     * @param proxy 代理对象
     * @param target 被代理对象
     * @param targetClass 被代理对象类对象
     * @param method 方法对象
     * @param arguments 方法参数
     * @param interceptorsAndDynamicMethodMatchers 切面列表，存放实现MethodInterceptor接口对象
     */
    public MethodInvocation(Object proxy, Object target, Class<?> targetClass, Method method, Object[] arguments, List<?> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object proceed() throws Throwable{
        // 切面执行完毕后再执行目标方法。
        if (this.currentInterceptorIndex==this.interceptorsAndDynamicMethodMatchers.size()-1){
            return this.method.invoke(this.target,this.arguments);
        }
        MethodInterceptor currentInterceptor=(MethodInterceptor)this.interceptorsAndDynamicMethodMatchers.get(++currentInterceptorIndex);
        return currentInterceptor.invoke(this);
    }



}
