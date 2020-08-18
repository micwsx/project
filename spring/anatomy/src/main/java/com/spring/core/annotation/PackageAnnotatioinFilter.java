package com.spring.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Arrays;

public class PackageAnnotatioinFilter implements AnnotationFilter {

    private final String[] prefixes;
    private final int hashCode;


    public PackageAnnotatioinFilter(String... packages) {
        this.prefixes = new String[packages.length];
        for (int i = 0; i < packages.length; i++) {
            String pkg = packages[i];
            this.prefixes[i] = pkg + ".";
        }
        Arrays.sort(this.prefixes);
        this.hashCode = Arrays.hashCode(this.prefixes);
    }

    @Override
    public boolean matches(Class<?> type) {
        return false;
    }

    @Override
    public boolean matches(String typeName) {
        for (String prefix : this.prefixes) {
            if (typeName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj){
            return true;
        }
        if (obj==null||getClass()!=obj.getClass()){
            return false;
        }
        return Arrays.equals(this.prefixes, ((PackageAnnotatioinFilter)obj).prefixes);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public String toString() {
        return "Packages annotation filter: "+this.prefixes;
    }
}
