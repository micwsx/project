package com.spring.core.annotation;

import java.lang.annotation.Annotation;
import java.util.*;

public interface MergedAnnotation<T extends Annotation> {

    String VALUE = "value";

    boolean isPresent();

    Class<T> getType();

    AnnotationAttributes asAnnotationAttributes(Adapt... adaptations);


    List<Class<? extends Annotation>> getMetaTypes();

    MergedAnnotation<T> withNonMergedAttributes();

    T synthesize() throws NoSuchElementException;

    Map<String, Object> asMap(Adapt... adaptations);

    boolean isDirectlyPresent();


    enum Adapt {
        CLASS_TO_STRING,
        ANNOTATION_TO_MAP;

        protected final boolean isIn(Adapt... adaptations) {
            for (Adapt adaptation : adaptations) {
                if (adaptation == this) {
                    return true;
                }
            }
            return false;
        }

        public static Adapt[] values(boolean classToString, boolean annotationToMap) {
            EnumSet<Adapt> result = EnumSet.noneOf(Adapt.class);
            addIfTrue(result, Adapt.CLASS_TO_STRING, classToString);
            addIfTrue(result, Adapt.ANNOTATION_TO_MAP, annotationToMap);
            return result.toArray(new Adapt[0]);
        }

        private static <T> void addIfTrue(Set<T> result, T value, boolean test) {
            if (test) {
                result.add(value);
            }
        }



    }
}
