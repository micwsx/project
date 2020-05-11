package com.enjoy.advisors;

import com.enjoy.config.DataSourceHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 拦截dao操作之前切换数据库连接
 */
@Aspect
@Component
public class DataSourceSwitchAdvisor {

    // 拦截所有com.enjoy.dao包及子包(..)的所有类(*)的匹配(update*,del*,add*)方法名写操作
    @Pointcut("execution(* com.enjoy.dao..*.update*(..)) " +"||"+
            " execution(* com.enjoy.dao..*.del*(..))"+"||"+
            " execution(* com.enjoy.dao..*.add*(..))"
    )
    public void masterPointCut(){}

    // 拦截所有读操作
    @Pointcut("execution(* com.enjoy.dao..*.find*(..)) " +"||"+
            " execution(* com.enjoy.dao..*.query*(..))"+"||"+
            " execution(* com.enjoy.dao..*.select*(..))"
    )
    public void slavePointCut(){}

    @Before("masterPointCut()")
    public void switchMasterAdvice(){
        DataSourceHolder.setMasterId();
    }

    /**
     * 为了本地测试，这里不切换成Slave数据库
     */
    @Before("slavePointCut()")
    public void switchSlaveAdvice(){
       DataSourceHolder.setMasterId();
        //DataSourceHolder.setSlaveId();
    }
}
