package com.spring.core.type;

import com.spring.core.annotation.MergedAnnotation;
import com.spring.core.annotation.MergedAnnotation.Adapt;
import com.spring.core.annotation.MergedAnnotationCollectors;
import com.spring.core.annotation.MergedAnnotationPredicates;
import com.spring.core.annotation.MergedAnnotations;
import com.spring.core.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface AnnotatedTypeMetadata {

    MergedAnnotations getAnnotations();

    default boolean isAnnotaed(String annotationName){
        return getAnnotations().isPresent(annotationName);
    }

    default Map<String,Object> getAnnotationAttributes(String annotationName,boolean classValuesAsString){
        MergedAnnotation<Annotation> annotation=getAnnotations().get(annotationName);
        if (!annotation.isPresent()){
            return null;
        }
        return annotation.asAnnotationAttributes(Adapt.values(classValuesAsString,true));
    }

    default MultiValueMap<String,Object> getAllAnnotationAttributes(String annotationName,boolean classValuesAsString){
        Adapt[] adaptations=Adapt.values(classValuesAsString, true);
        return getAnnotations().stream(annotationName)
                .filter(MergedAnnotationPredicates.unique(MergedAnnotation::getMetaTypes))
                .map(MergedAnnotation::withNonMergedAttributes)
                .collect(MergedAnnotationCollectors.toMultiValueMap(map->map.isEmpty()?null:map,adaptations));
    }





}
