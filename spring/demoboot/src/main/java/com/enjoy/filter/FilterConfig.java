package com.enjoy.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(MyCorsFilter myCorsFilter){
        FilterRegistrationBean registration=new FilterRegistrationBean();
        registration.setFilter(myCorsFilter);
        registration.addUrlPatterns("/*");
        registration.setName("Filter of Cors");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public MyCorsFilter myCorsFilter(){
        return new MyCorsFilter();
    }

}
