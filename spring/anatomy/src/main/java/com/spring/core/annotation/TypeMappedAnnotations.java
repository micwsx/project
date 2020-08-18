package com.spring.core.annotation;

import com.sun.istack.internal.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TypeMappedAnnotations implements MergedAnnotations {

    static final MergedAnnotations NONE = new TypeMappedAnnotations(
            null, new Annotation[0], RepeatableContainers.none(), AnnotationFilter.ALL);


    @Nullable
    private final Object source;

    @Nullable
    private final AnnotatedElement element;

    @Nullable
    private final SearchStrategy searchStrategy;

    @Nullable
    private final Annotation[] annotations;

    private final RepeatableContainers repeatableContainers;

    private final AnnotationFilter annotationFilter;

//    @Nullable
//    private volatile List<Aggregate> aggregates;

    private TypeMappedAnnotations(AnnotatedElement element, SearchStrategy searchStrategy,
                                  RepeatableContainers repeatableContainers, AnnotationFilter annotationFilter) {
        this.source = element;
        this.element = element;
        this.searchStrategy = searchStrategy;
        this.annotations = null;
        this.repeatableContainers = repeatableContainers;
        this.annotationFilter = annotationFilter;
    }

    private TypeMappedAnnotations(@Nullable Object source, Annotation[] annotations,
                                  RepeatableContainers repeatableContainers, AnnotationFilter annotationFilter) {
        this.source = source;
        this.element = null;
        this.searchStrategy = null;
        this.annotations = annotations;
        this.repeatableContainers = repeatableContainers;
        this.annotationFilter = annotationFilter;
    }

    @Override
    public <A extends Annotation> boolean isPresent(Class<A> annotationType) {
        return false;
    }

    @Override
    public boolean isPresent(String annotationType) {
        return false;
    }

    @Override
    public boolean isDirectlyPresent(String annotationType) {
        return false;
    }

    @Override
    public <A extends Annotation> MergedAnnotation<A> get(Class<A> annotationType) {
        return null;
    }

    @Override
    public <A extends Annotation> MergedAnnotation<A> get(Class<A> annotationType, Predicate<? super MergedAnnotation<A>> predicate) {
        return null;
    }

    @Override
    public <A extends Annotation> MergedAnnotation<A> get(Class<A> annotationType, Predicate<? super MergedAnnotation<A>> predicate, MergedAnnotationSelector<A> selector) {
        return null;
    }

    @Override
    public <A extends Annotation> MergedAnnotation<A> get(String annotationType) {
        return null;
    }

    @Override
    public <A extends Annotation> MergedAnnotation<A> get(String annotationType, Predicate<? super MergedAnnotation<A>> predicate) {
        return null;
    }

    @Override
    public <A extends Annotation> MergedAnnotation<A> get(String annotationType, Predicate<? super MergedAnnotation<A>> predicate, MergedAnnotationSelector<A> selector) {
        return null;
    }

    @Override
    public <A extends Annotation> Stream<MergedAnnotation<A>> stream(Class<A> annotationType) {
        return null;
    }

    @Override
    public <A extends Annotation> Stream<MergedAnnotation<A>> stream(String annotationType) {
        return null;
    }

    @Override
    public Stream<MergedAnnotation<Annotation>> stream() {
        return null;
    }

    @Override
    public Iterator<MergedAnnotation<Annotation>> iterator() {
        return null;
    }


    static MergedAnnotations from(AnnotatedElement element, SearchStrategy searchStrategy,
                                  RepeatableContainers repeatableContainers, AnnotationFilter annotationFilter) {

//        if (AnnotationsScanner.isKnownEmpty(element, searchStrategy)) {
//            return NONE;
//        }
        return new TypeMappedAnnotations(element, searchStrategy, repeatableContainers, annotationFilter);
    }

    static MergedAnnotations from(@Nullable Object source, Annotation[] annotations,
                                  RepeatableContainers repeatableContainers, AnnotationFilter annotationFilter) {

        if (annotations.length == 0) {
            return NONE;
        }
        return new TypeMappedAnnotations(source, annotations, repeatableContainers, annotationFilter);
    }
}
