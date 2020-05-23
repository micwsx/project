package feature.aop;

import org.springframework.lang.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectiveMethodInvoation implements ProxyMethodInvocation, Cloneable {

    protected final Object proxy;
    @Nullable
    protected final Object target;
    @Nullable
    private final Class<?> targetClass;

    protected final Method method;

    protected Object[] arguments;

    protected List<?> interceptorsAndDynamicMethodMatchers;

    private int currentInterceptorIndex = -1;

    @Nullable
    private Map<String, Object> userAttributes;

    /**
     * @param proxy                                代理对象
     * @param target                               被代理对象
     * @param targetClass                          被代理对象类对象
     * @param method                               方法对象
     * @param arguments                            方法参数
     * @param interceptorsAndDynamicMethodMatchers 切面列表，存放实现MethodInterceptor接口对象
     */
    public ReflectiveMethodInvoation(Object proxy, Object target, Class<?> targetClass, Method method, Object[] arguments, List<?> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    @Override
    public Object proceed() throws Throwable {
        // 切面执行完毕后再执行目标方法。
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(this.target, this.arguments);
        }
        MethodInterceptor currentInterceptor = (MethodInterceptor) this.interceptorsAndDynamicMethodMatchers.get(++currentInterceptorIndex);
        return currentInterceptor.invoke(this);
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.method;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    /******************ProxyMethodInvocation implementation*****************************/
    @Override
    public Object getProxy() {
        return this.proxy;
    }

    @Override
    public MethodInvocation invocableClone() {
        Object[] cloneArguments = this.arguments;
        if (this.arguments.length > 0) {
            cloneArguments = this.arguments.clone();
        }
        return invocableClone(cloneArguments);
    }

    @Override
    public MethodInvocation invocableClone(Object... arguments) {
        if (this.userAttributes == null) {
            this.userAttributes = new HashMap<>();
        }
        try {
            /*这里是浅复制，2个不同对象，但成员相同*/
            ReflectiveMethodInvoation clone = (ReflectiveMethodInvoation) clone();
            clone.arguments = arguments;
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new IllegalStateException(
                    "Should be able to clone object of type [" + getClass() + "]: " + ex);
        }
    }

    @Override
    public void setArguments(Object... arguments) {
        this.arguments = arguments;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            if (userAttributes == null) {
                this.userAttributes = new HashMap<>();
            }
            this.userAttributes.put(key, value);
        } else {
            if (this.userAttributes != null) {
                this.userAttributes.remove(key);
            }
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return this.userAttributes != null ? this.userAttributes.get(key) : null;
    }


    @Override
    public String toString() {
        // Don't do toString on target, it may be proxied.
        StringBuilder sb = new StringBuilder("ReflectiveMethodInvocation: ");
        sb.append(this.method).append("; ");
        if (this.target == null) {
            sb.append("target is null");
        }
        else {
            sb.append("target is of class [").append(this.target.getClass().getName()).append(']');
        }
        return sb.toString();
    }

}
