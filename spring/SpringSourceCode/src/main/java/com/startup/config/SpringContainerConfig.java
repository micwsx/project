package com.startup.config;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;


// 无web.xml启动时，只扫描com.enjoy下的包，排除@Controller注解类和com.enjoy.debris下的包
@ComponentScan(basePackages = {"com.enjoy"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.enjoy.debris.*")
        })
//@EnableCaching
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class SpringContainerConfig {


}
