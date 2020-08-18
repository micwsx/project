package anatomy.defaultValueMethod;

import anatomy.metadata.Michael;
import anatomy.metadata.Person;

import java.lang.annotation.*;
import java.lang.reflect.Method;

public class ClassWithDefautMethodValue {

    public int getInt() {
        return 1;
    }

    public Integer getInteger() {
        return 1;
    }

    @MethodAnnotation(value = "Michael")
    public String getName(String name) {
        return name;
    }

}

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MethodAnnotation {

    String key() default "default key";

    String value() default "default value";
}
