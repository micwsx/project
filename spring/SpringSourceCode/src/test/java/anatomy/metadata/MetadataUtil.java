package anatomy.metadata;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class MetadataUtil {




    public static void main(String[] args) {

//        Annotation[] annotations=Jack.class.getAnnotations();
        Annotation[] annotations=Jack.class.getDeclaredAnnotations();

        System.out.println(Arrays.toString(annotations));

    }
}
