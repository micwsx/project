package com.enjoy.config.redis;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class RedisCache implements Cache {

    private RedisTemplate<String,Object> redisTemplate;

    public RedisCache(String name) {
        this.name = name;
    }

    private String name;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }
    //获取数据
    @Override
    public ValueWrapper get(Object key) {
        Object result=redisTemplate.opsForValue().get(key);
        if (result!=null){
            ValueWrapper obj=new SimpleValueWrapper(result);
            System.out.println("------缓存获取到内容-------"+obj);
            return obj;
        }else{
            System.out.println("------缓存中内容不存在-------");
            return null;
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    // 加入缓存
    @Override
    public void put(Object key, Object value) {

            final String keyVal=key.toString();
            final Object valueValue=value;
            // 设置30s-60s之间的随机过期时间范围，防止缓存雪崩
            Random random=new Random();
            int timeout=random.nextInt(30)+30;
            redisTemplate.opsForValue().set(keyVal,valueValue,timeout, TimeUnit.SECONDS);
            System.out.println("键值存入缓存>>>>>>key:"+keyVal+"-value:"+valueValue+"-TTL"+timeout);
//        redisTemplate.execute(new RedisCallback<Long>(){
//            @Override
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                byte[] keyByte=keyVal.getBytes();
//                byte[] valueByte= SerializationUtils.serialize((Serializable)valueValue);
//                connection.set(keyByte,valueByte);
//                connection.expire(valueByte, 86400);
//                return 1L;
//            }
//        });
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    // 删除缓存
    @Override
    public void evict(Object key) {
        final String keyf=key.toString();
        System.out.println("-------緩存刪除键："+keyf+"------");
        redisTemplate.delete(keyf);
//        redisTemplate.execute(new RedisCallback<Long>() {
//            @Override
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                return connection.del(keyf.getBytes());
//            }
//        });
    }

    @Override
    public void clear() {
        System.out.println("-------緩存清理------");

        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
}
