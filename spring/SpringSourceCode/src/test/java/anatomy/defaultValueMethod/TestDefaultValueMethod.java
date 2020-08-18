package anatomy.defaultValueMethod;

import java.lang.reflect.Method;

public class TestDefaultValueMethod {

    public static void main(String[] args) {
        for (Method declaredMethod : ClassWithDefautMethodValue.class.getDeclaredMethods()) {
            Object defaultValue=declaredMethod.getDefaultValue();
            System.out.println(declaredMethod.getName()+"-"+defaultValue);
        }
        System.out.println("----------------------------------------------------");
        for (Method declaredMethod : MethodAnnotation.class.getDeclaredMethods()) {
            Object defaultValue=declaredMethod.getDefaultValue();
            System.out.println(declaredMethod.getName()+"-"+defaultValue);
        }



    }
}
