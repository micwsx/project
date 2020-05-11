package com.enjoy.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截service包及子包下所有方法名包括add*，save*，insert*，update*，delete*，remove*，添加PROPAGATION_REQUIRED事务隔离级别事务
 * get*，query*，find*，select*方法添加只读事务。
 */
@Configuration
public class GlobalTransactionConfiguration {

    private static final int TX_METHOD_TIMEOUT=5;
    private static final String AOP_POINTCUT_EXPRESSION="execution (* com.enjoy.service..*(..))";
    @Bean
    public PlatformTransactionManager platformTransactionManager(@Qualifier("dataSource") DataSource dataSource){
        DataSourceTransactionManager txManager=new DataSourceTransactionManager(dataSource);
        return txManager;
    }

    @Bean
    public TransactionInterceptor txAdvice(PlatformTransactionManager platformTransactionManager){
        //设置事务属性：隔离级别，只读等
        NameMatchTransactionAttributeSource txAttributeSource=new NameMatchTransactionAttributeSource();
        //只读事务
        RuleBasedTransactionAttribute readOnlyRule=new RuleBasedTransactionAttribute();
        readOnlyRule.setReadOnly(true);
        readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        RuleBasedTransactionAttribute requiredRule=new RuleBasedTransactionAttribute();
        requiredRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //超过5秒，回滚
        requiredRule.setTimeout(TX_METHOD_TIMEOUT);
        Map<String,TransactionAttribute> txMap=new HashMap<>();
        txMap.put("add*",requiredRule);
        txMap.put("save*", requiredRule);
        txMap.put("insert*",requiredRule);
        txMap.put("update*",requiredRule);
        txMap.put("delete*",requiredRule);
        txMap.put("remove*",requiredRule);
        txMap.put("get*",readOnlyRule);
        txMap.put("query*", readOnlyRule);
        txMap.put("find*", readOnlyRule);
        txMap.put("select*",readOnlyRule);
        txAttributeSource.setNameMap(txMap);
        TransactionInterceptor txAdvice=new TransactionInterceptor(platformTransactionManager,txAttributeSource);
        return txAdvice;
    }

    @Bean
    public Advisor txAdvisor(TransactionInterceptor txAdvice){
        DefaultPointcutAdvisor advisor=new DefaultPointcutAdvisor();
        AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        advisor.setPointcut(pointcut);
        advisor.setAdvice(txAdvice);
        return advisor;
    }
}
