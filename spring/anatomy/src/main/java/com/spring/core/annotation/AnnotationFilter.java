package com.spring.core.annotation;


import java.lang.annotation.Annotation;

@FunctionalInterface
public interface AnnotationFilter {

    AnnotationFilter PLAIN=packages("java.lang","org.springframework.lang");
    AnnotationFilter JAVA = packages("java", "javax");

    AnnotationFilter ALL = new AnnotationFilter() {
        @Override
        public boolean matches(Annotation annotation) {
            return true;
        }
        @Override
        public boolean matches(Class<?> type) {
            return true;
        }
        @Override
        public boolean matches(String typeName) {
            return true;
        }
        @Override
        public String toString() {
            return "All annotations filtered";
        }
    };

    default boolean matches(Annotation annotation) {
        return matches(annotation.annotationType());
    }

    default boolean matches(Class<?> type) {
        return matches(type.getName());
    }

    boolean matches(String typeName);

    static AnnotationFilter packages(String...packages){
        return new PackageAnnotatioinFilter(packages);
    }
}
