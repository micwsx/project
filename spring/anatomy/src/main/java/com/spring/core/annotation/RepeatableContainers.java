package com.spring.core.annotation;

import com.spring.core.util.Assert;
import com.spring.core.util.ObjectUtils;
import com.sun.istack.internal.Nullable;

import javax.management.Attribute;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Method;
import java.util.Objects;

public abstract class RepeatableContainers {

    @Nullable
    private final RepeatableContainers parent;


    private RepeatableContainers(@Nullable RepeatableContainers parent) {
        this.parent = parent;
    }

    public RepeatableContainers and(Class<? extends Annotation> container,
                                    Class<? extends Annotation> repeatable) {
        return new ExplicitRepeatableContainer(this, repeatable, container);
    }

    Annotation[] findRepeatabledAnnotations(Annotation annotation) {
        if (this.parent == null) return null;
        return this.parent.findRepeatabledAnnotations(annotation);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ObjectUtils.nullSafeEquals(this.parent, ((RepeatableContainers) obj).parent);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.parent);
    }

    public static RepeatableContainers standardRepeatables() {
        return StandardRepeatableContainers.INSTANCE;
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return null;
    }

    public static RepeatableContainers of(Class<? extends Annotation> repeatable, @Nullable Class<? extends Annotation> container) {
        return new ExplicitRepeatableContainer(null, repeatable, container);
    }

    public static RepeatableContainers none(){
        return null;
    }

    private static class StandardRepeatableContainers extends RepeatableContainers {

        private static StandardRepeatableContainers INSTANCE=new StandardRepeatableContainers();

        StandardRepeatableContainers() {
           super(null);
        }
    }

    private static class ExplicitRepeatableContainer extends RepeatableContainers {

        private final Class<? extends Annotation> repeatable;
        private final Class<? extends Annotation> container;
        private final Method valueMethod;

        private ExplicitRepeatableContainer(@Nullable RepeatableContainers parent,
                                            Class<? extends Annotation> repeatable,
                                            @Nullable Class<? extends Annotation> container) {
            super(parent);
            Assert.notNull(repeatable, "Repeatable must not be null");
            if (container == null) {
                container = deduceContainer(repeatable);
            }
            Method valueMethod=AttributeMethods.forAnnotationType(container).get(MergedAnnotation.VALUE);
//            try {
//                if (valueMethod==null)
//                    throw new NoSuchMethodException("No value method found");
//            } catch (AnnotationConfigurationException ex) {
//                e.printStackTrace();
//            }

            this.valueMethod = null;
            this.container = container;
            this.repeatable = repeatable;
        }

        private Class<? extends Annotation> deduceContainer(Class<? extends Annotation> repeatable) {
            Repeatable annotation = repeatable.getAnnotation(Repeatable.class);
            Assert.notNull(annotation, () -> "Annotation type must be a repeatable annotation: " +
                    "failed to resolve container type for " + repeatable.getName());
            return annotation.value();
        }

        @Override
        public RepeatableContainers and(Class<? extends Annotation> container, Class<? extends Annotation> repeatable) {
            return super.and(container, repeatable);
        }


    }


}
