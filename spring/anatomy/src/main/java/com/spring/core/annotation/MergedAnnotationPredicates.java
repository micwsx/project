package com.spring.core.annotation;

import com.spring.core.util.Assert;
import com.spring.core.util.ObjectUtils;
import com.sun.istack.internal.Nullable;

import java.lang.annotation.Annotation;
import java.security.KeyException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class MergedAnnotationPredicates {

    public MergedAnnotationPredicates() {
    }

    public static <A extends Annotation> Predicate<MergedAnnotation<? extends A>> typeIn(String... typeNames) {
        return annotation -> ObjectUtils.containsElement(typeNames, annotation.getType().getName());
    }


    public static <A extends Annotation> Predicate<MergedAnnotation<? extends A>> typeIn(Class<?>... types) {
        return annotation -> ObjectUtils.containsElement(types, annotation.getType());
    }

    public static <A extends Annotation> Predicate<MergedAnnotation<? extends A>> typeIn(Collection<?> types) {
        return annotation -> types.stream()
                .map(type -> type instanceof Class ? ((Class<?>) type).getName() : ((Object) type).toString())
                .anyMatch(typeName -> typeName.equals(annotation.getType().getName()));
    }

    public static <A extends Annotation> Predicate<MergedAnnotation<A>> firstRunOf(
            Function<? super MergedAnnotation<A>, ?> valueExtractor) {
        return new FirstRunOfPredicate(valueExtractor);
    }

    public static <A extends Annotation, K> Predicate<MergedAnnotation<A>> unique(
            Function<? super MergedAnnotation<A>, K> keyExtractor) {
        return new UniquePredicat(keyExtractor);
    }

    private static class FirstRunOfPredicate<A extends Annotation, K> implements Predicate<MergedAnnotation<A>> {

        private final Function<? super MergedAnnotation<A>, ?> valueExtractor;

        private boolean hasLastValue;

        @Nullable
        private Object lastValue;

        public FirstRunOfPredicate(Function<? super MergedAnnotation<A>, K> valueExtractor){
            Assert.notNull(valueExtractor, "Value extractor must not be null");
            this.valueExtractor=valueExtractor;
        }

        @Override
        public boolean test(MergedAnnotation<A> annotation) {
           if (!this.hasLastValue){
               this.hasLastValue=true;
               this.lastValue=this.valueExtractor.apply(annotation);
           }
           Object value=this.valueExtractor.apply(annotation);
           return ObjectUtils.nullSafeEquals(value, this.lastValue);
        }
    }


    private static class UniquePredicat<A extends Annotation, K> implements Predicate<MergedAnnotation<A>> {

        private final Function<? super MergedAnnotation<A>, K> keyExtractor;

        private final Set<K> seen = new HashSet<>();

        public UniquePredicat(Function<? super MergedAnnotation<A>, K> keyExtractor){
            Assert.notNull(keyExtractor, "Key extractor must not be null");
            this.keyExtractor=keyExtractor;
        }

        @Override
        public boolean test(@Nullable MergedAnnotation<A> annotation) {
            K key=this.keyExtractor.apply(annotation);
            return this.seen.add(key);
        }
    }


}
