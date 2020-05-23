package feature.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public interface MethodInvocation extends Invocation{
    Method getMethod();
}

interface Invocation extends Jointpoint{
    Object[] getArguments();
}

interface Jointpoint{
    Object proceed() throws Throwable;
    Object getThis();
    AccessibleObject getStaticPart();
}