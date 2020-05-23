package feature.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class AroundMethodInterceptor implements MethodInterceptor {

    protected final Class<?> targetClass;
    protected final Method method;

    /**
     * @param targetClass 方法执行类对象
     * @param method      方法对象
     */
    public AroundMethodInterceptor(Class<?> targetClass, Method method) {
        this.targetClass = targetClass;
        this.method = method;
    }

    private Object invokeAroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return this.method.invoke(this.targetClass.newInstance(), proceedingJoinPoint);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (!(methodInvocation instanceof ProxyMethodInvocation)) {
            throw new IllegalStateException("MethodInvocation is not a Spring ProxyMethodInvocation: " + methodInvocation);
        }
        ProxyMethodInvocation proxyMethodInvocation = (ProxyMethodInvocation) methodInvocation;
        ProceedingJoinPoint proceedingJoinPoint=new MethodInvocationProceedingJoinPoint(proxyMethodInvocation);
        // 调用指定的Around方法，在Around方法内部用户自己调用proceed()方法传递。
        return invokeAroundMethod(proceedingJoinPoint);
    }
}

class MethodInvocationProceedingJoinPoint implements ProceedingJoinPoint, JoinPoint.StaticPart {

    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final ProxyMethodInvocation methodInvocation;

    @Nullable
    private Object[] args;

    /**
     * Lazily initialized signature object.
     */
    @Nullable
    private Signature signature;

    /**
     * Lazily initialized source location object.
     */
    @Nullable
    private SourceLocation sourceLocation;

    public MethodInvocationProceedingJoinPoint(ProxyMethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    @Override
    public void set$AroundClosure(AroundClosure aroundClosure) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object proceed() throws Throwable {
        MethodInvocation clone=this.methodInvocation.invocableClone();
        return clone.proceed();
//        return this.methodInvocation.proceed();
    }

    @Override
    public Object proceed(Object[] objects) throws Throwable {
        if (objects.length != this.methodInvocation.getArguments().length) {
            throw new IllegalArgumentException("Expecting " +
                    this.methodInvocation.getArguments().length + " arguments to proceed, " +
                    "but was passed " + objects.length + " arguments");
        }
        this.methodInvocation.setArguments(objects);
        return this.methodInvocation.invocableClone(objects).proceed();
    }

    @Override
    public String toShortString() {
        return "execution(" + getSignature().toShortString() + ")";
    }

    @Override
    public String toLongString() {
        return "execution(" + getSignature().toLongString() + ")";
    }

    @Override
    public Object getThis() {
        return this.methodInvocation.getProxy();
    }

    @Override
    public Object getTarget() {
        return this.methodInvocation.getThis();
    }

    @Override
    public Object[] getArgs() {
        if (this.args == null) {
            this.args = this.methodInvocation.getArguments().clone();
        }
        return this.args;
    }

    @Override
    public Signature getSignature() {
        if (this.signature == null) {
            this.signature = new MethodSignatureImpl();
        }
        return this.signature;
    }

    @Override
    public SourceLocation getSourceLocation() {
        if (this.sourceLocation == null) {
            this.sourceLocation = new SourceLocationImpl();
        }
        return this.sourceLocation;
    }

    @Override
    public String getKind() {
        return ProceedingJoinPoint.METHOD_EXECUTION;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public StaticPart getStaticPart() {
        return null;
    }

    class MethodSignatureImpl implements MethodSignature {

        @Nullable
        private volatile String[] parameterNames;

        @Override
        public Class getReturnType() {
            return methodInvocation.getMethod().getReturnType();
        }

        @Override
        public Method getMethod() {
            return methodInvocation.getMethod();
        }

        @Override
        public Class[] getParameterTypes() {
            return methodInvocation.getMethod().getParameterTypes();
        }

        @Override
        public String[] getParameterNames() {
            if (this.parameterNames != null) {
                this.parameterNames = parameterNameDiscoverer.getParameterNames(getMethod());
            }
            return this.parameterNames;
        }

        @Override
        public Class[] getExceptionTypes() {
            return methodInvocation.getMethod().getExceptionTypes();
        }


        @Override
        public String getName() {
            return methodInvocation.getMethod().getName();
        }

        @Override
        public int getModifiers() {
            return methodInvocation.getMethod().getModifiers();
        }

        @Override
        public Class getDeclaringType() {
            return methodInvocation.getMethod().getDeclaringClass();
        }

        @Override
        public String getDeclaringTypeName() {
            return methodInvocation.getMethod().getDeclaringClass().getName();
        }

        @Override
        public String toShortString() {
            return toString(false, false, false, false);
        }

        @Override
        public String toLongString() {
            return toString(true, true, true, true);
        }

        @Override
        public String toString() {
            return toString(false, true, false, true);
        }


        private String toString(boolean includeModifier, boolean includeReturnTypeAndArgs,
                                boolean useLongReturnAndArgumentTypeName, boolean useLongTypeName) {

            StringBuilder sb = new StringBuilder();
            if (includeModifier) {
                sb.append(Modifier.toString(getModifiers()));
                sb.append(" ");
            }
            if (includeReturnTypeAndArgs) {
                appendType(sb, getReturnType(), useLongReturnAndArgumentTypeName);
                sb.append(" ");
            }
            appendType(sb, getDeclaringType(), useLongTypeName);
            sb.append(".");
            sb.append(getMethod().getName());
            sb.append("(");
            Class<?>[] parametersTypes = getParameterTypes();
            appendTypes(sb, parametersTypes, includeReturnTypeAndArgs, useLongReturnAndArgumentTypeName);
            sb.append(")");
            return sb.toString();
        }

        private void appendTypes(StringBuilder sb, Class<?>[] types, boolean includeArgs,
                                 boolean useLongReturnAndArgumentTypeName) {

            if (includeArgs) {
                for (int size = types.length, i = 0; i < size; i++) {
                    appendType(sb, types[i], useLongReturnAndArgumentTypeName);
                    if (i < size - 1) {
                        sb.append(",");
                    }
                }
            }
            else {
                if (types.length != 0) {
                    sb.append("..");
                }
            }
        }

        private void appendType(StringBuilder sb, Class<?> type, boolean useLongTypeName) {
            if (type.isArray()) {
                appendType(sb, type.getComponentType(), useLongTypeName);
                sb.append("[]");
            }
            else {
                sb.append(useLongTypeName ? type.getName() : type.getSimpleName());
            }
        }
    }

    class SourceLocationImpl implements SourceLocation {

        @Override
        public Class<?> getWithinType() {
            if (methodInvocation.getThis() == null) {
                throw new UnsupportedOperationException("No source location joinpoint available: target is null");
            }
            return methodInvocation.getThis().getClass();
        }

        @Override
        public String getFileName() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getLine() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int getColumn() {
            throw new UnsupportedOperationException();
        }
    }
}
