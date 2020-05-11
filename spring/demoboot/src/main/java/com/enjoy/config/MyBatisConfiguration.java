package com.enjoy.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@MapperScan("com.enjoy.dao")
public class MyBatisConfiguration {
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:/sqlmapper/mybatis-setting.xml"));
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.enjoy.model");
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/sqlmapper/*Mapper.xml"));
//        return sqlSessionFactoryBean;
//    }
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setBasePackage("com.enjoy.dao");
//        return mapperScannerConfigurer;
//    }
}
