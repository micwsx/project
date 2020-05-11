package com.enjoy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

@Configuration
public class DataSourceConfiguration {

    private String driverClassName;
    private String slaveUrl;
    private String username;
    private String password;

    @Autowired
    private Environment env;

    @Bean
    public DataSource cp30DataSource() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        try {
            comboPooledDataSource.setDriverClass(env.getProperty("spring.datasource.driver-class-name"));
            comboPooledDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
            comboPooledDataSource.setUser(env.getProperty("spring.datasource.username"));
            comboPooledDataSource.setPassword(env.getProperty("spring.datasource.password"));
            comboPooledDataSource.setMinPoolSize(Integer.parseInt(env.getProperty("spring.datasource.minPoolSize")));
            comboPooledDataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("spring.datasource.maxPoolSize")));
            comboPooledDataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("spring.datasource.maxIdleTime")));
            comboPooledDataSource.setAcquireIncrement(Integer.parseInt(env.getProperty("spring.datasource.acquireIncrement")));
            comboPooledDataSource.setMaxStatements(Integer.parseInt(env.getProperty("spring.datasource.maxStatements")));
            comboPooledDataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("spring.datasource.initialPoolSize")));
            comboPooledDataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getProperty("spring.datasource.idleConnectionTestPeriod")));
            comboPooledDataSource.setAcquireRetryAttempts(Integer.parseInt(env.getProperty("spring.datasource.acquireRetryAttempts")));
            comboPooledDataSource.setBreakAfterAcquireFailure(Boolean.parseBoolean(env.getProperty("spring.datasource.breakAfterAcquireFailure")));
            comboPooledDataSource.setTestConnectionOnCheckout(Boolean.parseBoolean(env.getProperty("spring.datasource.testConnectionOnCheckout")));
            comboPooledDataSource.setAcquireRetryDelay(Integer.parseInt(env.getProperty("spring.datasource.acquireRetryDelay")));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return comboPooledDataSource;
    }

//    @Bean(initMethod = "init", destroyMethod = "close")
    @Bean
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.druid.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.druid.slaveUrl"));
        dataSource.setUsername(env.getProperty("spring.druid.username"));
        dataSource.setPassword(env.getProperty("spring.druid.password"));
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
