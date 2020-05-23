package com.enjoy.debris.bean;

import com.enjoy.debris.aop.MyAnnotation;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImp implements UserService {

    @MyAnnotation(exp = "#name")
    public String getName(String name) {
        return "打印名称：" + name;
    }
}
