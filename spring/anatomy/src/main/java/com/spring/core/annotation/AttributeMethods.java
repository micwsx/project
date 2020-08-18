package com.spring.core.annotation;

import com.spring.core.util.Assert;
import com.spring.core.util.ReflectionUtils;
import com.sun.istack.internal.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解方法类
 */
final class AttributeMethods {

    static final AttributeMethods NONE = new AttributeMethods(null, new Method[0]);
    private static final Map<Class<? extends Annotation>, AttributeMethods> cache =
            new ConcurrentHashMap<>();

    private static final Comparator<Method> methodComparator = (m1, m2) -> {
        if (m1 != null && m2 != null) {
            return m1.getName().compareTo(m2.getName());
        }
        return m1 != null ? -1 : 1;
    };

    private final Class<? extends Annotation> annotationType;
    private final Method[] attributeMethods;
    private final boolean[] canThrowTypeNotPresentException;
    private final boolean hasDefaultValueMethod;
    private final boolean hasNestedAnnotation;

    public AttributeMethods(@Nullable Class<? extends Annotation> annotationType, Method[] attributeMethods) {
        this.annotationType = annotationType;
        this.attributeMethods = attributeMethods;
        this.canThrowTypeNotPresentException = new boolean[attributeMethods.length];
        boolean foundDefaultValueMethod = false;
        boolean foundNestedAnnotation = false;
        for (int i = 0; i < attributeMethods.length; i++) {
            Method method = attributeMethods[i];
            Class<?> returnType = method.getReturnType();
            if (method.getDefaultValue() != null) {
                foundDefaultValueMethod = true;
            }
            if (returnType.isAnnotation() || (returnType.isArray() && returnType.getComponentType().isAnnotation())) {
                foundNestedAnnotation = true;
            }
            ReflectionUtils.makeAccessible(method);
            this.canThrowTypeNotPresentException[i] = (returnType == Class.class || returnType == Class[].class || returnType.isEnum());
        }
        this.hasDefaultValueMethod = foundDefaultValueMethod;
        this.hasNestedAnnotation = foundNestedAnnotation;
    }

    /**
     * Determine whether this instance only contains a single attribute named value.
     *
     * @return
     */
    private boolean hasOnlyValueAttribute() {
        return this.attributeMethods.length == 1 && MergedAnnotation.VALUE.equals(this.attributeMethods[0].getName());
    }

    private boolean isValid(Annotation annotation) {
        assertAnnotation(annotation);
        for (int i = 0; i < size(); i++) {
            if (canThrowTypeNotPresentException(i)) {
                try {
                    get(i).invoke(annotation);
                } catch (Throwable ex) {
                    return false;
                }
            }
        }
        return true;
    }

    private void validate(Annotation annotation) {
        assertAnnotation(annotation);
        for (int i = 0; i < size(); i++) {
            if (canThrowTypeNotPresentException(i)) {
                try {
                    get(i).invoke(annotation);
                } catch (Throwable ex) {
                    throw new IllegalStateException("Could not obtain annotation attribute value for " +
                            get(i).getName() + " declared on " + annotation.annotationType(), ex);
                }
            }
        }
    }


    @Nullable
     Method get(String name) {
        int index = indexOf(name);
        return index != -1 ? this.attributeMethods[index] : null;
    }

    int indexOf(String name) {
        for (int i = 0; i < this.attributeMethods.length; i++) {
            if (this.attributeMethods[i].getName().equals(name))
                return i;
        }
        return -1;
    }

     Method get(int index) {
        return this.attributeMethods[index];
    }


    private int size() {
        return this.attributeMethods.length;
    }

    private void assertAnnotation(Annotation annotation) {
        Assert.notNull(annotation, "Annotation must not be null");
        if (this.annotationType != null) {
            Assert.isInstanceOf(this.annotationType, annotation);
        }
    }

    boolean canThrowTypeNotPresentException(int index) {
        return this.canThrowTypeNotPresentException[index];
    }

    private int indexOf(Method attribute) {
        for (int i = 0; i < this.attributeMethods.length; i++) {
            if (this.attributeMethods[i].equals(attribute))
                return i;
        }
        return -1;
    }

    private boolean hasDefaultValueMethod() {
        return this.hasDefaultValueMethod;
    }

    private boolean hasNestedAnnotation() {
        return this.hasNestedAnnotation;
    }

    static AttributeMethods forAnnotationType(Class<? extends Annotation> annotationType) {
        if (annotationType == null) {
            return NONE;
        }
        return cache.computeIfAbsent(annotationType, AttributeMethods::compute);
    }

    private static AttributeMethods compute(Class<? extends Annotation> annotationType) {
        Method[] methods = annotationType.getDeclaredMethods();
        int size = methods.length;
        for (int i = 0; i < methods.length; i++) {
            if (isAttributeMethod(methods[i])) {
                methods[i] = null;
                size--;
            }
        }
        if (size == 0) return NONE;
        Arrays.sort(methods, methodComparator);

        Method[] attributeMethods = Arrays.copyOf(methods, size);
        return new AttributeMethods(annotationType, attributeMethods);
    }

    private static boolean isAttributeMethod(Method method) {
        return (method.getParameterCount() == 0 && method.getReturnType() != void.class);
    }

    private static String describe(@Nullable Method attribute) {
        if (attribute == null) return "(none)";
        return describe(attribute.getDeclaringClass().getClass(), attribute.getName());
    }


    private static String describe(@Nullable Class<?> annotationType, @Nullable String attributeName) {
        if (attributeName == null) {
            return "(none)";
        }
        String in = (annotationType != null ? " in annotation [" + annotationType.getName() + "]" : "");
        return "attribute '" + attributeName + "'" + in;

    }

}
