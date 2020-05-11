package com.enjoy.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
}
