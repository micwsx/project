package com.research.spel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FieldValueTestBean {

    //    @Value("#{systemProperties['user.country']}")
    private String defaultLocale;

//    public FieldValueTestBean(@Value("#{systemProperties['user.country']}") String defaultLocale) {
//        this.defaultLocale = defaultLocale;
//    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    //必须添加@Autowired不然@Value不能注入值
    @Autowired
    public void test(@Value("#{systemProperties['user.country']}") String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

//    @Value("#{systemProperties['user.country']}")
//    public void setDefaultLocale(String defaultLocale) {
//        this.defaultLocale = defaultLocale;
//    }
}
