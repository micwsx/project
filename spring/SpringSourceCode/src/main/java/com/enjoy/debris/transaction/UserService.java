package com.enjoy.debris.transaction;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class UserService {

    //@MyAnnotation(name = "#{#who}.#{#name}")
    public String myAnnotionMethod(String who,String name){
        System.out.println("执行了自己注释测试方法。"+who+":"+name);
        return "["+who+"]";
    }

    public void del(){
        System.out.println("UserService.del()方法已执行！！！");
    }

    @PostConstruct
     public void init(){
        System.out.println("UserService.init()");
     }

     @PreDestroy
     public void destroy(){
         System.out.println("UserService.destroy()");
     }

}
