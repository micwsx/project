package feature.aop;


public interface MethodInterceptor {
    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
