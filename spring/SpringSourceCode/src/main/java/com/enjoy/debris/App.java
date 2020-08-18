package com.enjoy.debris;

import com.enjoy.debris.bean.BeanOne;
import com.enjoy.debris.bean.UserService;
import com.enjoy.debris.bean.UserServiceImp;
import com.enjoy.debris.scope.DependScopeBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(TestSpringConfig.class);
        ctx.refresh();

        BeanOne beanOne = (BeanOne)ctx.getBean("beanOne");
        System.out.println(beanOne.getBeanTwo());

        BeanOne beanOne2 = (BeanOne)ctx.getBean("beanOne2");
        System.out.println(beanOne2.getBeanTwo());

//        Scope测试
//        DependScopeBean bean = ctx.getBean(DependScopeBean.class);
//        System.out.println(bean.getScopeBean());
//        System.out.println(ctx.getBean(DependScopeBean.class).getScopeBean());

//        AOP测试
//        UserService userService= ctx.getBean(UserService.class);
//       userService.getName("Michael");

    }
}
