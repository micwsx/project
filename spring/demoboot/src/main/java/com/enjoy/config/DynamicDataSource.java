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


public class DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware {

    @Override
    protected Object determineCurrentLookupKey() {
        String finalId= DataSourceHolder.getId();
        System.out.println("determineCurrentLookupKey: "+finalId);
        return finalId;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataSource dataSource=(DataSource)applicationContext.getBean("cp30DataSource");
        DataSource druidDataSource=(DataSource)applicationContext.getBean("druidDataSource");
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceHolder.MASTER_DB ,dataSource);
        targetDataSources.put(DataSourceHolder.SLAVE_DB, druidDataSource);
        setTargetDataSources(targetDataSources);
        setDefaultTargetDataSource(dataSource);
    }
}
