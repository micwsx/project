package segment;

import com.enjoy.model.Role;
import com.enjoy.service.dao.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:application.xml"})
public class SpringJunitWithXmlTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void mybtais2(){
        RoleService roleService= applicationContext.getBean(RoleService.class);
        List<Role> roles= roleService.selectAll();
        for (int i = 0; i < roles.size(); i++) {
            System.out.println(roles.get(i));
        }
    }

}
