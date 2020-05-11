package com.enjoy.debris.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Component
@PropertySource("classpath:db.properties")
public class DataSourceConfiguration {

    @Value("${jdbc.driverClassName}")
    private  String driver;
    @Value("${jdbc.url}")
    private  String url;
    @Value("${jdbc.username}")
    private  String username;
    @Value("${jdbc.password}")
    private  String password;

    @Bean
    public DataSource dataSource(){
        ComboPooledDataSource comboPooledDataSource=new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(driver);
            comboPooledDataSource.setJdbcUrl(url);
            comboPooledDataSource.setUser(username);
            comboPooledDataSource.setPassword(password);
            comboPooledDataSource.setMinPoolSize(10);
            comboPooledDataSource.setMaxPoolSize(100);
            comboPooledDataSource.setMaxIdleTime(1800);
            comboPooledDataSource.setAcquireIncrement(3);
            comboPooledDataSource.setMaxStatements(1000);
            comboPooledDataSource.setInitialPoolSize(10);
            comboPooledDataSource.setIdleConnectionTestPeriod(60);
            comboPooledDataSource.setAcquireRetryAttempts(30);
            comboPooledDataSource.setBreakAfterAcquireFailure(false);
            comboPooledDataSource.setTestConnectionOnCheckout(false);
            comboPooledDataSource.setAcquireRetryDelay(100);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    return  comboPooledDataSource;
    }


}
