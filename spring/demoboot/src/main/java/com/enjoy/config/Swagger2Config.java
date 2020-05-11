package com.enjoy.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8090/swagger-ui.html
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enable}") //开启访问接口文档的权限
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo测试模块")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enjoy.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("MICWSX公司API文档")
                        .description("此文档仅供开发技术组领导、开发人员使用")   //描述
                        .version("1.0")
                        .contact(new Contact("Michael", "http://www.mic.com", "michael@gmail.com"))
                        .license("The Apache License")
                        .licenseUrl("http://www.license.com")
                        .termsOfServiceUrl("http://www.xxx.com/")   //相关的网址
                        .build());
    }
}
