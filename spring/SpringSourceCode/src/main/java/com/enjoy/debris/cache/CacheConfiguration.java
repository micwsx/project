package com.enjoy.debris.cache;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@EnableCaching
@Component
public class CacheConfiguration {


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
        RedisCache cache=new RedisCache("redisCache");
        cache.setRedisTemplate(redisTemplate);
        return cache;
    }

    @Bean
    public FactoryBean<ConcurrentMapCache> mapCache() {
        ConcurrentMapCacheFactoryBean bean = new ConcurrentMapCacheFactoryBean();
        bean.setName("mapCache");
        return bean;
    }

    @Bean
    public CacheManager simpleCacheManager(@Qualifier("redisCache") Cache redisCache, @Qualifier("mapCache") Cache concurrentMapCacheFactoryBean) {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        List<Cache> list = new ArrayList<>();
        list.add(redisCache);
        list.add(concurrentMapCacheFactoryBean);
        simpleCacheManager.setCaches(list);
        return simpleCacheManager;
    }



}
