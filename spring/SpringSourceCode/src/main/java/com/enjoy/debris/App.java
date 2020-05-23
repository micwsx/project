package com.enjoy.debris;

import com.enjoy.debris.bean.UserService;
import com.enjoy.debris.bean.UserServiceImp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext();
        ctx.register(TestSpringConfig.class);
        ctx.refresh();

        //AOP测试
        UserService userService= ctx.getBean(UserService.class);
       userService.getName("Michael");

    }
}
