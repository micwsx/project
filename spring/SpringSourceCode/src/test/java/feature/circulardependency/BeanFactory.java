package feature.circulardependency;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

    // 记录当前创建bean名称
    private final Set<String> isSingletonCurrentlyInCreateion=new HashSet<>();
    // 一级缓存 存放完全实例化对象
    private final Map<String,Object> singletonObjects=new ConcurrentHashMap<String,Object>();
    // 三级缓存 存放实例化对象（还未注入依赖对象）
    private final Map<String,Object> singletonFactories=new ConcurrentHashMap<String,Object>();
    // 存放beanName和className
    private final Map<String,String> beanDefinitionMap=new HashMap<String,String>();

    public BeanFactory() {
        // 提前获取Bean名称和Bean类限定名
        beanDefinitionMap.put("user","feature.circulardependency.User");
        beanDefinitionMap.put("account","feature.circulardependency.Account");
    }

    public Object getBean(String beanName) throws Throwable {
        //尝试从一级缓存获取对象实例
        Object beanInstance=singletonObjects.get(beanName);
        if (beanInstance==null&&isSingletonCurrentlyInCreateion.contains(beanName)){
            System.out.println("从三级缓存获取对象实例 "+beanName);
            beanInstance=singletonFactories.get(beanName);
        }
        if (beanInstance!=null){
            return beanInstance;
        }

        System.out.println(beanName+" 正在创建。。。");
        isSingletonCurrentlyInCreateion.add(beanName);

        String clazzName=beanDefinitionMap.get(beanName);

        // 创建实例
        beanInstance=Class.forName(clazzName).newInstance();
        // 提前暴露保存到三级缓存中
        System.out.println(beanName+" 保存到三级缓存中。");
        singletonFactories.put(beanName,beanInstance);

        System.out.println(beanName+" 赋值依赖对象.");
        resolveDependency(beanInstance);
        // 从三级缓存中移除
        System.out.println(beanName+" 三级缓存中移除.");
        singletonFactories.remove(beanName);
        // 保存到一级缓存
        System.out.println(beanName+" 保存到一级缓存.");
        singletonObjects.put(beanName,beanInstance);

        return beanInstance;
    }

    private void resolveDependency(Object instance) throws Throwable {

        Field[] fields= instance.getClass().getDeclaredFields();
        for (Field field:fields){
            // 字段是引用类型
            if (!field.getType().isPrimitive()){
                String beanName= field.getName();
                Object obj=getBean(beanName);
                if (obj!=null){
                    // 判断是否是private访问类型
                    if ((!Modifier.isPublic(field.getModifiers())||
                            !Modifier.isPublic(field.getDeclaringClass().getModifiers())||
                            Modifier.isFinal(field.getModifiers()))&&!field.isAccessible()){
                        field.setAccessible(true);
                    }
                    // 赋值
                    field.set(instance, obj);
                }
            }
        }
    }

    public static void main(String[] args) {
        BeanFactory beanFactory=new BeanFactory();
        try {
            User user= (User) beanFactory.getBean("user");
            System.out.println(user);
            if (user!=null){
                Account account=user.getAccount();
                System.out.println(account);
                System.out.println(account.getUser());
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
