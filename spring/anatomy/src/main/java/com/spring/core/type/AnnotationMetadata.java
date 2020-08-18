package com.spring.core.type;

import com.spring.core.annotation.MergedAnnotation;
import com.spring.core.annotation.MergedAnnotations;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public interface AnnotationMetadata extends ClassMetadata, AnnotatedTypeMetadata{

    default Set<String> getAnnotationTypes(){
        return getAnnotations().stream().filter(MergedAnnotation::isDirectlyPresent)
                .map(annotation->annotation.getType().getName())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    default Set<String> getMetaAnnotationTypes(String annotationName){
        MergedAnnotation<?> annotation=getAnnotations().get(annotationName,MergedAnnotation::isDirectlyPresent);
        if (!annotation.isPresent())
            return Collections.emptySet();
        return MergedAnnotations.from(annotation.getType(), MergedAnnotations.SearchStrategy.INHERITED_ANNOTATION).stream()
                .map(mergedAnnotation->mergedAnnotation.getType().getName())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
