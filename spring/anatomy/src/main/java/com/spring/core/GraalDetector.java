package com.spring.core;

abstract class GraalDetector {
    private static final boolean imageCode=System.getProperty("org.graalvm.nativeimage.imagecode")!=null;

    GraalDetector(){}

    public static boolean inImageCode(){return imageCode;}
}
