package segment;

import com.enjoy.model.Role;
import com.enjoy.model.User;
import com.enjoy.service.dao.RoleService;
import com.startup.config.SpringContainerConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class SpringUnitWithAnnotation {

    @Test
    public void mybtaisWrite() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringContainerConfig.class);
        RoleService roleService = annotationConfigApplicationContext.getBean(RoleService.class);
        roleService.update(new Role(6, "Normal"));
    }

    @Test
    public void mybtaisRead() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringContainerConfig.class);
        RoleService roleService = annotationConfigApplicationContext.getBean(RoleService.class);
        List<Role> roles = roleService.selectAll();
        for (int i = 0; i < roles.size(); i++) {
            System.out.println(roles.get(i));
        }
    }

    @Test
    public void mongodbTest() {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringContainerConfig.class);
        MongoTemplate mongoTemplate = annotationConfigApplicationContext.getBean(MongoTemplate.class);

        String dbName = mongoTemplate.getDb().getName();
        System.out.println(dbName);

        User user = new User(110, "MICHAEL", true, "TEST ACCOUNT!");

        Set<String> sets = mongoTemplate.getCollectionNames();

        System.out.println(sets);
        mongoTemplate.insert(user);

        //mongoTemplate.insert(user,"suser");

//        List<User> users = mongoTemplate.findAll(User.class);
//        System.out.println(users);

    }


    @Test
    public void resource() {

//        File file=new File(SpringContainerConfig.class.getClassLoader().getResource("").getPath()).getParentFile();
//        String fullPath=file.getPath()+File.separator+"classes"+File.separator+"db.properties";
//        System.out.println(new File(fullPath).exists());
//
//        try {
//            String path= ResourceUtils.getURL("classpath:").getPath();
//            System.out.println(path);
//        }catch (Exception ex){
//
//        }
        // ResourceBundle主要是获取多语言资源文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("", Locale.getDefault());
        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
    }


    @Test
    public void legacy() {
//        final AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext(SpringContainerConfig.class);
//        SpringContainerConfig dataSource=annotationConfigApplicationContext.getBean(SpringContainerConfig.class);
//        System.out.println(dataSource);

//        new Thread(()->{
//            Object object = annotationConfigApplicationContext.getBean(DataSource.class);
//            System.out.println(object);
//        }).start();

//        ScopeBean scopeBean=annotationConfigApplicationContext.getBean(ScopeBean.class);
//        System.out.println(scopeBean);
//        Thread thread=new Thread(new Runnable(){
//            @Override
//            public void run() {
//                ScopeBean obj=annotationConfigApplicationContext.getBean(ScopeBean.class);
//                System.out.println(obj);
//            }
//        });
//        thread.start();

//        TestTransaction testTransaction= annotationConfigApplicationContext.getBean(TestTransaction.class);
//        testTransaction.print();
    }
}
