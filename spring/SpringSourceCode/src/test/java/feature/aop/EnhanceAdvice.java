package feature.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

public class EnhanceAdvice {

    public void before() {
        System.out.println("EnhanceAdvice.before()");
    }

    public void after() {
        System.out.println("EnhanceAdvice.after()");
    }

    public void afterReturning() {
        System.out.println("EnhanerAdvice.afterReturning()");
    }


    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("---EnhanceAdvice.around()开始----");
        Object result = null;
        try {
            System.out.println("执行目标方法");
            result = proceedingJoinPoint.proceed();
            System.out.println("执行结果：" + result);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("---EnhanceAdvice.around()结束----");
        return result;
    }

    public void afterThrowing() {
        System.out.println("EnhanceAdvice.afterThrowing()");
    }

}
