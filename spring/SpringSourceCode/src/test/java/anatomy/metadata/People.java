package anatomy.metadata;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//被注解的类的子类，也会拥有此注解。
@Inherited
@Indexed
public @interface People {

}
