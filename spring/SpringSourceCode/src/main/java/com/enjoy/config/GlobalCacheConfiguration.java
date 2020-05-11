package com.enjoy.config;

import com.enjoy.debris.cache.RedisCache;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.interceptor.*;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 业务操作和数据库操作结果都添加缓存。
 * 拦截dao包及子包下所有方法名包含 get*，query*，find*，select*方法添加只读Redis缓存。
 * 拦截service包及子包下所有方法名包含 get*，query*，find*，select*方法添加只读Redis缓存。
 */
@Configuration
public class GlobalCacheConfiguration {

    private static final String REDIS_CACHE_NAME="redisCache";
    private static final String MAP_CACHE_NAME="mapCache";

    //拦截所有com.enjoy.dao包及子包(..)的所有类所有方法所有返回类型
    private static final String CACHE_POINTCUT_EXPRESSION="execution (* com.enjoy.dao..*.*(..)) || execution (* com.enjoy.service..*.*(..))";

    @Bean
    public CacheInterceptor cacheAdvice(){
        NameMatchCacheOperationSource nameMatchCacheOperationSource=new NameMatchCacheOperationSource();
        Map<String, Collection<CacheOperation>> cacheMap=new HashMap<>();
        Collection<CacheOperation> redisCache=new ArrayList<CacheOperation>();
        CacheableOperation.Builder builder=new CacheableOperation.Builder();
        builder.setCacheName(REDIS_CACHE_NAME);
        builder.setKeyGenerator("cacheKeyGenerator");
        CacheOperation cacheOperation=new CacheableOperation(builder);
        redisCache.add(cacheOperation);
        cacheMap.put("get*",redisCache);
        cacheMap.put("query*", redisCache);
        cacheMap.put("find*", redisCache);
        cacheMap.put("select*",redisCache);
        nameMatchCacheOperationSource.setNameMap(cacheMap);
        CacheInterceptor cacheAdvice=new CacheInterceptor();
        cacheAdvice.setCacheOperationSource(nameMatchCacheOperationSource);
        return cacheAdvice;
    }
    @Bean
    public Advisor cacheAdvisor(CacheInterceptor cacheAdvice){
        DefaultPointcutAdvisor advisor=new DefaultPointcutAdvisor();
        AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
        pointcut.setExpression(CACHE_POINTCUT_EXPRESSION);
        advisor.setPointcut(pointcut);
        advisor.setAdvice(cacheAdvice);
        return advisor;
    }

    //key的生成，springcache的内容，跟具体实现缓存器无关
    //自定义本项目内的key的方式
    @Bean
    public KeyGenerator cacheKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getSimpleName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public Cache redisCache(RedisTemplate redisTemplate){
        RedisCache cache=new RedisCache(REDIS_CACHE_NAME);
        cache.setRedisTemplate(redisTemplate);
        return cache;
    }

    @Bean
    public FactoryBean<ConcurrentMapCache> mapCache() {
        ConcurrentMapCacheFactoryBean bean = new ConcurrentMapCacheFactoryBean();
        bean.setName(MAP_CACHE_NAME);
        return bean;
    }

    @Bean
    public CacheManager simpleCacheManager(@Qualifier(REDIS_CACHE_NAME) Cache redisCache, @Qualifier(MAP_CACHE_NAME) Cache concurrentMapCacheFactoryBean) {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        List<Cache> list = new ArrayList<>();
        list.add(redisCache);
        list.add(concurrentMapCacheFactoryBean);
        simpleCacheManager.setCaches(list);
        return simpleCacheManager;
    }

}
