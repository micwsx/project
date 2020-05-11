package com.enjoy.debris.aop;


import com.enjoy.debris.customAnnotation.MyAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAdvice {
    // com.enjoy.service..子包下所有类的del开头方法
    @Pointcut(value = "execution(public * com.enjoy.service..*.del*(..))")
    public void del() {
    }


    @Around(value = "@annotation(myAnnotation)")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint, MyAnnotation myAnnotation) throws Throwable{
       System.out.println("-------------1.LogAdvice.myAround()---------------");
       Method method=((MethodSignature)proceedingJoinPoint.getSignature()).getMethod();
       String argValue=getKey(myAnnotation.name(),method,proceedingJoinPoint.getArgs());
       System.out.println("表达式："+myAnnotation.name()+"　参数值:"+argValue);
       Object result = proceedingJoinPoint.proceed();
       System.out.println("-------------2.LogAdvice.myAround()---------------"+result);
       return result;
    }

    // 核心逻辑调用异常退出后，执行。
    @AfterThrowing(value = "del()", throwing = "ex")
    public int afterThrowing(JoinPoint joinpoint, Throwable ex) {
        System.out.println("-------------LogAdvice.afterThrowing():" + ex.getMessage() + "---------------");
        return 0;
    }

    @Around("del()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("-------------1.LogAdvice.around()---------------");
        Object result = proceedingJoinPoint.proceed();
        System.out.println("-------------2.LogAdvice.around()---------------"+result);
        return result;
    }

    @Before("del()")
    public void before(JoinPoint joinpoint) {
        System.out.println("-------------LogAdvice.before()---------------");
    }

    // 核心逻辑调用后(包括正常或异常退出)，执行
    @After("del()")
    public void after(JoinPoint joinpoint) {
        System.out.println("-------------LogAdvice.after()---------------");
    }

    // 不管是是否有返回值，正常退出后，执行。
    @AfterReturning(value = "del()")
    public void afterReturning(JoinPoint joinpoint) {
        System.out.println("-------------LogAdvice.afterReturning()---------------");
    }


    private String getKey(String exp,Method method,Object[] args){
      // 获取参数名
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer=new LocalVariableTableParameterNameDiscoverer();
        String[] argNames=localVariableTableParameterNameDiscoverer.getParameterNames(method);
        SpelExpressionParser parser=new SpelExpressionParser();
        Expression expression=parser.parseExpression(exp,new TemplateParserContext());
        if (args.length<=0){
            return  null;
        }
        EvaluationContext context=new StandardEvaluationContext();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(argNames[i],args[i]);
        }
        return expression.getValue(context,String.class);
    }
}
