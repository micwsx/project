package segment;

import com.enjoy.dao.TRole;
import com.enjoy.model.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class MyBatisTest {

    private  SqlSessionFactory sqlSessionFactory;
    @Before
    public void init(){
        try {
            // 1.加载mybatis配置文件
            String resource="sqlmapper/mybatis-config.xml";
            InputStream inputStream= Resources.getResourceAsStream(resource);
            // 2.创建SqlSessionFactory
            sqlSessionFactory= new SqlSessionFactoryBuilder().build(inputStream);
            System.out.println("成功加载mybatis-config.xml配置文件");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void selectAllRole(){
        // 3.获取SqlSession对象
        SqlSession sqlSession= sqlSessionFactory.openSession();
        // 4.创建代理对象
        TRole roleMapper= sqlSession.getMapper(TRole.class);
        // 5.执行对象方法
        List<Role> roles= roleMapper.selectAll();
        for (int i = 0; i < roles.size(); i++) {
            System.out.println(roles.get(i));
        }
    }
}
