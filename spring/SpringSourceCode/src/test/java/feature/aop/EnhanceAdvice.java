package feature.aop;

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

    public void afterThrowing() {
        System.out.println("EnhanceAdvice.afterThrowing()");
    }

}
