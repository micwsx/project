package com.enjoy.debris.cache;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class TestCache {

    public static void main(String[] args) {




        final AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext("com.enjoy.debris");
        //缓存效果测试
        CacheService cacheService= annotationConfigApplicationContext.getBean(CacheService.class);
        System.out.println(cacheService);
//        //测试key生成规则
//        KeyGenerator keyGenerator= annotationConfigApplicationContext.getBean(KeyGenerator.class);
//       try {
//           Object generateKeyValue=keyGenerator.generate(cacheService,CacheService.class.getMethod("queryRedisData",String.class),"007");
//           System.out.println("生成的键值为：　"+generateKeyValue.toString());
//       }catch (Exception ex){
//           System.out.println(ex.getMessage());
//       }


        //User{id='007', name='noKey', password='只有参数id', age=1}
//        User user007= cacheService.query007("007");
//        System.out.println(user007);

        for (int i = 0; i < 5; i++) {
            User user007= cacheService.put007(new User("00"+i,"007","007",i));
            System.out.println(user007);
        }


//        User user007= cacheService.query007WithName("007","Michael");
//        User user007= cacheService.query007WithKeyGenerator("007","Michael");
//        cacheService.putRedisCache(new User("008","Michael","123",24));
//        User user=cacheService.queryRedisData("009");
//


//        String _010=cacheService.getCache("010");
//        System.out.println(_010);

//        CacheManager bean = annotationConfigApplicationContext.getBean(CacheManager.class);
//        Cache cache= bean.getCache("redisCache");
//        Cache.ValueWrapper valueWrapper= cache.get("008");
//        System.out.println(valueWrapper.get());

        // 缓存功能测试
//        CacheManager bean = annotationConfigApplicationContext.getBean(CacheManager.class);
//        //String cacheUniqueName="mapCache";
//        String cacheUniqueName="redisCache";
//        Cache cache = bean.getCache(cacheUniqueName);
//        cache.put("name","michael--------");
//        Cache.ValueWrapper cacheName = cache.get("name");
//       cache.evict("name");
//        cacheName = cache.get("name");
//        System.out.println(cacheName==null?"":cacheName.get());





    }
}
