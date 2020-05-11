package com.enjoy.debris.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@Component
public class TransactionConfig {

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
        DataSourceTransactionManager platformTransactionManager=new DataSourceTransactionManager();
        platformTransactionManager.setDataSource(dataSource);
        return  platformTransactionManager;
    }

//    @Component
//    public class TransactionManagementConfigurerBean implements TransactionManagementConfigurer{
//        @Autowired
//        private DataSource dataSource;
//        @Override
//        public PlatformTransactionManager annotationDrivenTransactionManager() {
//            DataSourceTransactionManager platformTransactionManager=new DataSourceTransactionManager();
//            platformTransactionManager.setDataSource(dataSource);
//            return  platformTransactionManager;
//        }
//    }

}
