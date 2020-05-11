package com.enjoy.debris.cache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

// ２缓存都存放
//@CacheConfig(cacheNames = {"mapCache","redisCache"})
@CacheConfig(cacheNames = {"redisCache"})
@Component
public class CacheService {
    // 键值就是cacheKeyGenerator生成的名字：CacheServicequery007WithKeyGenerator007Michael
    @Cacheable(value = "redisCache",keyGenerator = "cacheKeyGenerator")
    public User query007WithKeyGenerator(String id,String name){
        User user=new User(id,"noKey","只有参数id",1);
        System.out.println("查询条件id结果已存放redis缓存中："+id+" user结果："+user);
        return user;
    }

    // 默认键值就是SimpleKey [id,name]
    @Cacheable
    public User query007WithName(String id,String name){
        User user=new User(id,"noKey","只有参数id",1);
        System.out.println("查询条件id结果已存放redis缓存中："+id+" user结果："+user);
        return user;
    }

    // 默认键值就是id
    @Cacheable
    public User query007(String id){
        User user=new User(id,"noKey","只有参数id",1);
        System.out.println("查询条件id结果已存放redis缓存中："+id+" user结果："+user);
        return user;
    }

    @Cacheable(value = "redisCache",keyGenerator = "cacheKeyGenerator")
    public User put007(User user){
        return user;
    }


    //只存不读 每次都真实查询数据库，把数据带到内存来;
    @CachePut(key = "#user.id")
    public User putRedisCache(User user) {
        System.out.println("对象user已存放redis缓存中："+user);
        return  user;
    }

   @Cacheable(key = "#id")
   public User queryRedisData(String id){
        User user=new User(id,"michael","queryRedisData",1111);
        System.out.println("queryRedisData()方法已执行（查询条件id：结果）已存放redis缓存中："+id+" user结果："+user);
        return user;
   }

   //值就是michael{id}
    @CacheEvict(beforeInvocation = true,allEntries=true)//方法执行前删除缓存
    @Cacheable(key = "'michael' + #id")
    public String getCache(String id) {
        return "MICHAEL";
    }



    //存放map内存
    @Cacheable(cacheNames = "mapCache",key = "'michael' + #id")
    public String mapCache(String id) {
        return "数据存储在map中";
    }
}
