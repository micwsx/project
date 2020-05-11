package com.enjoy.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware {

    @Override
    protected Object determineCurrentLookupKey() {
        String finalId= DataSourceHolder.getId();
        System.out.println("determineCurrentLookupKey: "+finalId);
        return finalId;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataSource dataSource=(DataSource)applicationContext.getBean("dataSource");
        DataSource druidDataSource=(DataSource)applicationContext.getBean("druidDataSource");
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceHolder.MASTER_DB ,dataSource);
        targetDataSources.put(DataSourceHolder.SLAVE_DB, druidDataSource);
        setTargetDataSources(targetDataSources);
        setDefaultTargetDataSource(dataSource);
    }

    //  https://localhost:8090/druid/index.html
    /**
     * 配置访问druid监控
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStateViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //初始化参数initParams
        //添加白名单
        servletRegistrationBean.addInitParameter("allow","");
        //添加ip黑名单
        // servletRegistrationBean.addInitParameter("deny","192.168.16.111");
        //登录查看信息的账号密码
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","123");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    /**
     * 过滤不需要监控的后缀
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
