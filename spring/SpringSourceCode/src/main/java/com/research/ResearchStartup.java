package com.research;

import com.research.spel.FieldValueTestBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.StandardEnvironment;

//@Lazy
//@Primary
//@DependsOn
//@Role(1)
//@Description("test")
@Configuration("full")
//@Conditional({})
//@PropertySource("classpath:db.properties")
//@PropertySources(
//        {
//                @PropertySource("classpath:db.properties")
//        }
//)
@ComponentScan(basePackages = {"com.research"})
public class ResearchStartup {

    // 国际化bean名称一定是messageSource
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("i18n.message");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ResearchStartup.class);
        // StandardEnvironment包括了System.properties和Environment两种属性。
        StandardEnvironment standardEnvironment = (StandardEnvironment) ctx.getEnvironment();
//        激活profile
//        standardEnvironment.setActiveProfiles("development");
        ctx.refresh();
//          获取属性
//        if (standardEnvironment.containsProperty("jdbc.masterUrl")) {
//            String masterUrl = standardEnvironment.getProperty("jdbc.masterUrl");
//            System.out.println(masterUrl);
//        }

//        B_Order order = ctx.getBean(B_Order.class);
//        B_User user = ctx.getBean(B_User.class);
//        System.out.println(order);
//        System.out.println(user);

//        国际化
//        String languageValue = ctx.getMessage("lang", null, Locale.SIMPLIFIED_CHINESE);
//        System.out.println(languageValue);

//        BlackListEvent事件
//        EmailService emailService = ctx.getBean(EmailService.class);
//        emailService.sendEmail("Michael.wu@computop.com", "Test email");

//         2.Reousrces
//        org.springframework.core.io.Resource
//       实现类UrlResource(URL,files,HTTP,FTP或其它), ClassPathResource(classpath),FileSystemResource(File),ServletContextResource,InputStreamResource,ByteArrayResource
//        ResourceLoader
//        ctx.getResource("classpath:some/template.txt"),添加classpath前缀转换成ClassPathResource
//        ctx.getResource("file:some/template.txt"),添加classpath前缀转换成FileSystemResource
//        ctx.getResource("http:some/template.txt"),添加classpath前缀转换成ClassPathResource
//        如果没有前缀前根据ctx对象类型返回对应类型
//        ClassPathXmlApplicationContext.getResource()返回就是ClassPathResource
//        FileSystemXmlApplicationContext.getResource()返回就是FileSystemResource
//        WebApplicationContext.getResource()返回就是ServletContextResource
//        ctx.getResource("some/template.txt")返回对象类型

//        3.Validation, Data Binding, and Type Conversion
//        Validation = org.springframework.validation.Validator+DataBinder
//        BeanWrapper
//        BeanWrapper beanWrapper=new BeanWrapperImpl(new Person());
//        beanWrapper.setPropertyValue("name", "Michael");
//        String nameValue=(String)beanWrapper.getPropertyValue("name");
//        System.out.println(nameValue);

//        PropertyEditor实现类都在包org.springframework.beans.propertyeditors下：
//        ByteArrayPropertyEditor,
//        ClassEditor,
//        CustomBooleanEditor,
//        CustomCollectionEditor,
//        CustomDateEditor,
//        CustomNumberEditor,
//        FileEditor,
//        InputStreamEditor,
//        LocaleEditor,
//        PatternEditor,
//        PropertiesEditor,
//        StringTrimmerEditor,
//        URLEditor,




        FieldValueTestBean bean= ctx.getBean(FieldValueTestBean.class);
        System.out.println(bean.getDefaultLocale());


    }
}
