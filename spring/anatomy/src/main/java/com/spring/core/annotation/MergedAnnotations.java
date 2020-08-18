package com.spring.core.annotation;

import com.sun.istack.internal.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.spring.core.annotation.TypeMappedAnnotations.NONE;

public interface MergedAnnotations extends Iterable<MergedAnnotation<Annotation>> {

    <A extends Annotation> boolean isPresent(Class<A> annotationType);

    boolean isPresent(String annotationType);

    boolean isDirectlyPresent(String annotationType);


    <A extends Annotation> MergedAnnotation<A> get(Class<A> annotationType);

    <A extends Annotation> MergedAnnotation<A> get(Class<A> annotationType,
                                                   @Nullable Predicate<? super MergedAnnotation<A>> predicate);

    <A extends Annotation> MergedAnnotation<A> get(Class<A> annotationType,
                                                   @Nullable Predicate<? super MergedAnnotation<A>> predicate,
                                                   @Nullable MergedAnnotationSelector<A> selector);


    <A extends Annotation> MergedAnnotation<A> get(String annotationType);

    <A extends Annotation> MergedAnnotation<A> get(String annotationType,
                                                   @Nullable Predicate<? super MergedAnnotation<A>> predicate);

    <A extends Annotation> MergedAnnotation<A> get(String annotationType,
                                                   @Nullable Predicate<? super MergedAnnotation<A>> predicate,
                                                   @Nullable MergedAnnotationSelector<A> selector);

    <A extends Annotation> Stream<MergedAnnotation<A>> stream(Class<A> annotationType);

    <A extends Annotation> Stream<MergedAnnotation<A>> stream(String annotationType);

    Stream<MergedAnnotation<Annotation>> stream();

    static MergedAnnotations from(AnnotatedElement element, SearchStrategy searchStrategy) {
        return from(element, searchStrategy, RepeatableContainers.standardRepeatables());
    }

    static MergedAnnotations from(AnnotatedElement element, SearchStrategy searchStrategy,
                                  RepeatableContainers repeatableContainers) {

        return TypeMappedAnnotations.from(element, searchStrategy, repeatableContainers, AnnotationFilter.PLAIN);
    }



    static MergedAnnotations from(AnnotatedElement annotatedElement) {
        return null;
    }

    enum SearchStrategy{
        DIRECT,
        INHERITED_ANNOTATION,
        SUPERCLASS,
        TYPE_HIERARCHY,
        TYPE_HIERARCHY_AND_ENCLOSING_CLASSES
    }


}
