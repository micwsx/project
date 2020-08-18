package com.spring.core.annotation;

import com.spring.core.util.Assert;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;

public class AnnotationAttributes extends LinkedHashMap<String,Object> {

    private static final String UNKNOWN="unknown";

    private final Class<? extends Annotation> annotationType;

    private String displayName;
    private boolean validated=false;

    public AnnotationAttributes(){
        this.annotationType=null;
        this.displayName=UNKNOWN;
    }

    public AnnotationAttributes(int initialCapacity){
        super(initialCapacity);
        this.annotationType=null;
        this.displayName=UNKNOWN;
    }

    public AnnotationAttributes(AnnotationAttributes other){
        super(other);
        this.annotationType=other.annotationType;
        this.displayName=other.displayName;
        this.validated=other.validated;
    }

    public AnnotationAttributes(Class<? extends Annotation> annotationType){
        Assert.notNull(annotationType, "'annotationType' must not be null");
        this.annotationType=annotationType;
        this.displayName=annotationType.getName();
    }








}
