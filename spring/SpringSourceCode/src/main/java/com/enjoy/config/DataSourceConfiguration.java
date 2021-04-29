package com.enjoy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

@Component
@PropertySource("classpath:db.properties")
public class DataSourceConfiguration {
    @Value("${jdbc.driverClassName}")
    private  String driver;
    @Value("${jdbc.masterUrl}")
    private  String masterUrl;
    @Value("${jdbc.slaveUrl}")
    private  String slaveUrl;
    @Value("${jdbc.username}")
    private  String username;
    @Value("${jdbc.password}")
    private  String password;
    @Value("${jdbc.minPoolSize}")
    private Integer minPoolSize;
    @Value("${jdbc.maxPoolSize}")
    private Integer maxPoolSize;
    @Value("${jdbc.maxIdleTime}")
    private Integer maxIdleTime;
    @Value("${jdbc.acquireIncrement}")
    private Integer acquireIncrement;
    @Value("${jdbc.maxStatements}")
    private Integer maxStatements;
    @Value("${jdbc.initialPoolSize}")
    private Integer initialPoolSize;
    @Value("${jdbc.idleConnectionTestPeriod}")
    private Integer idleConnectionTestPeriod;
    @Value("${jdbc.acquireRetryAttempts}")
    private Integer acquireRetryAttempts;
    @Value("${jdbc.breakAfterAcquireFailure}")
    private boolean breakAfterAcquireFailure;
    @Value("${jdbc.testConnectionOnCheckout}")
    private boolean testConnectionOnCheckout;
    @Value("${jdbc.acquireRetryDelay}")
    private Integer acquireRetryDelay;

    @Resource
    private Environment environment;

    @Bean
    public DataSource defaultDataSource(){
        // spring默认datasource
        DataSource springDatasource=new DriverManagerDataSource();
        return springDatasource;
    }

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(driver);
            comboPooledDataSource.setJdbcUrl(masterUrl);
            comboPooledDataSource.setUser(username);
            comboPooledDataSource.setPassword(password);
            comboPooledDataSource.setMinPoolSize(minPoolSize);
            comboPooledDataSource.setMaxPoolSize(maxPoolSize);
            comboPooledDataSource.setMaxIdleTime(maxIdleTime);
            comboPooledDataSource.setAcquireIncrement(acquireIncrement);
            comboPooledDataSource.setMaxStatements(maxStatements);
            comboPooledDataSource.setInitialPoolSize(initialPoolSize);
            comboPooledDataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
            comboPooledDataSource.setAcquireRetryAttempts(acquireRetryAttempts);
            comboPooledDataSource.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
            comboPooledDataSource.setTestConnectionOnCheckout(testConnectionOnCheckout);
            comboPooledDataSource.setAcquireRetryDelay(acquireRetryDelay);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return comboPooledDataSource;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(slaveUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        // 配置初始化大小、最小、最大
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);// 配置获取连接等待超时的时间
        dataSource.setTimeBetweenEvictionRunsMillis(60000);// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);// 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setDefaultAutoCommit(false);// 禁止自动提交，实现事务管理
        dataSource.setValidationQuery("SELECT 1;");
        dataSource.setValidationQueryTimeout(30000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(50);
        // 拦截配置
        dataSource.setFilters("stat,wall");
        return dataSource;
    }

}
