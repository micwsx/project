package com.enjoy.debris.customAnnotation;


import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@RequestMapping
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface MyAnnotation {
    public String name() default "";
}
