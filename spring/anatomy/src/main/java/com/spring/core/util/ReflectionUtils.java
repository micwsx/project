package com.spring.core.util;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ReflectionUtils {

    public static final MethodFilter USER_DECLARED_METHODS =
            (method -> !method.isBridge() && !method.isSynthetic());

    public static final FieldFilter COPYABLE_FIELDS =
            (field -> !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())));

    @FunctionalInterface
    public interface MethodCallback {
        void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
    }

    @FunctionalInterface
    public interface MethodFilter {
        boolean matches(Method method);
    }

    @FunctionalInterface
    public interface FieldCallback {
        void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
    }

    @FunctionalInterface
    public interface FieldFilter {
        boolean matches(Field field);
    }

    private static final String CGLIB_RENAMED_METHOD_PREFIX = "CGLIB$";

    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

    private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentHashMap<>(256);

    private static final Map<Class<?>, Field[]> declaredFieldCache = new ConcurrentHashMap<>(256);


    public static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method or field: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {

        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    public static void handleInvocationTargeException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    public static void rethrowException(Throwable ex) throws Exception {
        if (ex instanceof RuntimeException)
            throw (RuntimeException) ex;
        if (ex instanceof Error)
            throw (Error) ex;
        throw new UndeclaredThrowableException(ex);
    }

    public static <T> Constructor<T> accessiableConstructor(Class<T> clazz, Class<?>... parameterTypes) throws NoSuchMethodException {
        Constructor<T> ctor = clazz.getDeclaredConstructor(parameterTypes);
        makeAccessible(ctor);
        return ctor;
    }

    public static void makeAccessible(Constructor<?> ctor) {
        boolean flag1 = !Modifier.isPublic(ctor.getModifiers());
        boolean flag2 = Modifier.isPublic(ctor.getDeclaringClass().getModifiers());
        boolean flag3 = ctor.isAccessible();
        if ((!flag1 || !flag2) && !flag3)
            ctor.setAccessible(true);
    }

    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, EMPTY_CLASS_ARRAY);
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() :
                    getDeclaredMethods(searchType, false));
            for (Method method : methods) {
                if (name.equals(method.getName()) && (paramTypes == null || hasSameParams(method, paramTypes)))
                    return method;
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * 判断方法是否相同的参数
     *
     * @param method
     * @param paramTypes
     * @return
     */
    private static boolean hasSameParams(Method method, Class<?>[] paramTypes) {
        return (paramTypes.length == method.getParameterCount() &&
                Arrays.equals(paramTypes, method.getParameterTypes()));
    }

    /**
     * 执行无参数方法
     *
     * @param method
     * @param target
     * @return
     */
    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, EMPTY_OBJECT_ARRAY);
    }

    /**
     * 执行方法
     *
     * @param method
     * @param target
     * @param paramTypes
     * @return
     */
    public static Object invokeMethod(Method method, Object target, Object... paramTypes) {
        try {
            return method.invoke(target, paramTypes);
        } catch (Exception e) {
            handleReflectionException(e);
        }
        throw new IllegalStateException("Should never get here");
    }

    /**
     * 判断方法是否有指定声名的异常
     *
     * @param method
     * @param exceptionType
     * @return
     */
    public static boolean declaresException(Method method, Class<?> exceptionType) {
        Class<?>[] declareExceptions = method.getExceptionTypes();
        for (Class<?> declareException : declareExceptions) {
            if (declareException.isAssignableFrom(exceptionType)) {
                return true;
            }
        }
        return false;
    }

    public static void doWithLocalMethods(Class<?> clazz, MethodCallback mc) {
        Method[] methods = getDeclaredMethods(clazz, false);
        for (Method method : methods) {
            try {
                mc.doWith(method);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + ex);
            }
        }
    }

    public static void doWithMethods(Class<?> clazz, MethodCallback mc) {
        doWithMethods(clazz, mc, null);
    }

    public static void doWithMethods(Class<?> clazz, MethodCallback mc, @Nullable MethodFilter mf) {
        Method[] methods = getDeclaredMethods(clazz, false);
        for (Method method : methods) {
            if (mf != null && mf.matches(method)) {
                continue;
            }
            try {
                mc.doWith(method);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + ex);
            }
        }
        //执行基类的方法
        if (clazz.getSuperclass() != null && (mf != USER_DECLARED_METHODS || clazz.getSuperclass() != Object.class)) {
            doWithMethods(clazz.getSuperclass(), mc, mf);
        } else if (clazz.isInterface()) {
            // 执行接口的方法
            for (Class<?> superIfc : clazz.getInterfaces()) {
                doWithMethods(superIfc, mc, mf);
            }
        }
    }

    public static Method[] getAllDeclaredMethods(Class<?> leafClass) {
        final List<Method> methods = new ArrayList<>(32);
        doWithMethods(leafClass, methods::add);
        return methods.toArray(EMPTY_METHOD_ARRAY);
    }

    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass) {
        return null;
    }

    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass, @Nullable MethodFilter mf) {
        final List<Method> methods = new ArrayList<>(32);
        doWithMethods(leafClass, method -> {
            boolean knownSignature = false;
            Method methodBeingOverriddenWithCovariantReturnType = null;
            for (Method existingMethod : methods) {
                if (method.getName().equals(existingMethod.getName()) &&
                        method.getParameterCount() == existingMethod.getParameterCount() &&
                        Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes())) {

                    if (existingMethod.getReturnType() != method.getReturnType()
                            && existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                        methodBeingOverriddenWithCovariantReturnType = existingMethod;
                    } else {
                        knownSignature = true;
                    }
                    break;
                }
            }
            if (methodBeingOverriddenWithCovariantReturnType != null) {
                methods.remove(methodBeingOverriddenWithCovariantReturnType);
            }
            if (!knownSignature && !isCglibRenamedMethod(method)) {
                methods.add(method);
            }

        }, mf);
        return methods.toArray(EMPTY_METHOD_ARRAY);
    }


    /**
     * 获取clazz类所有声名方法，包括本身声名公共，保护，默认，私有方法和接口实现的方法。
     *
     * @param clazz
     * @param defensive
     * @return
     */
    private static Method[] getDeclaredMethods(Class<?> clazz, boolean defensive) {
        Method[] result = declaredMethodsCache.get(clazz);
        if (result == null) {
            try {
                // 类或接口声名的所有方法（公共，保护，默认，私有），不包括继承的方法
                Method[] declaredMethods = clazz.getDeclaredMethods();
                List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
                if (defaultMethods != null) {
                    result = new Method[defaultMethods.size() + declaredMethods.length];
                    System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                    int index = declaredMethods.length;
                    for (Method defaultMethod : defaultMethods) {
                        result[index] = defaultMethod;
                        index++;
                    }
                } else {
                    result = declaredMethods;
                }
                declaredMethodsCache.put(clazz, result.length == 0 ? EMPTY_METHOD_ARRAY : result);
            } catch (Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                        "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
            }
        }
        return (result.length == 0 || !defensive) ? result : result.clone();
    }

    /**
     * 获取clazz接口实现方法列表
     *
     * @param clazz
     * @return
     */
    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method method : ifc.getMethods()) {
                if (!Modifier.isAbstract(method.getModifiers())) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(method);
                }
            }
        }
        return result;
    }

    /**
     * 判断是否是equals方法
     *
     * @param method
     * @return
     */
    public static boolean isEqualsMethod(@Nullable Method method) {
        if (method == null || !method.getName().equals("equals")) {
            return false;
        }
        if (method.getParameterCount() != 1) {
            return false;
        }
        return method.getParameterTypes()[0] == Object.class;
    }

    /**
     * 判断是否是hashCode方法
     *
     * @param method
     * @return
     */
    public static boolean isHashCodeMethod(@Nullable Method method) {
        return (method != null && method.getName().equals("hashCode") && method.getParameterCount() == 0);
    }

    /**
     * 判断是否是toString方法。
     *
     * @return
     */
    public static boolean isToStringMethod(@Nullable Method method) {
        return (method != null && method.getName().equals("toString") && method.getParameterCount() == 0);
    }

    /**
     * 判断是否是Object.class方法。
     *
     * @return
     */
    public static boolean isObjectMethod(@Nullable Method method) {
        return (method != null && (method.getDeclaringClass() == Object.class
                || isEqualsMethod(method) || isHashCodeMethod(method) || isToStringMethod(method)));

    }

    /**
     * Determine whether the given method is a CGLIB 'renamed' method,
     * following the pattern "CGLIB$methodName$0".
     *
     * @param renamedMethod the method to check
     */
    public static boolean isCglibRenamedMethod(Method renamedMethod) {
        String name = renamedMethod.getName();
        if (name.startsWith(CGLIB_RENAMED_METHOD_PREFIX)) {
            int i = name.length() - 1;
            while (i > 0 && Character.isDigit(name.charAt(i))) {
                i--;
            }
            return (i > CGLIB_RENAMED_METHOD_PREFIX.length() && (i < name.length() - 1) && name.charAt(i) == '$');
        }
        return false;
    }

    public static void makeAccessible(Method method){
        if((!Modifier.isPublic(method.getModifiers())||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers()))&&!method.isAccessible()){
            method.setAccessible(true);
        }
    }

    // Field handling
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    public static Field findField(Class<?> clazz, String name, @Nullable Class<?> type) {
        Class<?> searchType = clazz;
        while (Object.class != searchType && searchType != null) {
            Field[] fields = getDeclaredFields(searchType);
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    public static void setField(Field field, @Nullable Object target, @Nullable Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
        }
    }

    public static Object getField(Field field, @Nullable Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            handleReflectionException(e);
        }
        throw new IllegalStateException("Should never get here");
    }

    public static void doWithLocalFields(Class<?> clazz, FieldCallback fc) {
        for (Field field : getDeclaredFields(clazz)) {
            try {
                fc.doWith(field);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
            }
        }
    }

    public static void doWithFields(Class<?> clazz, FieldCallback fc) {
        doWithFields(clazz, fc, null);
    }

    public static void doWithFields(Class<?> clazz, FieldCallback fc, @Nullable FieldFilter ff) {
        Class<?> targetClass = clazz;
        do {

            Field[] fields = getDeclaredFields(clazz);
            for (Field field : fields) {
                if (ff != null && ff.matches(field)) {
                    continue;
                }
                if (fc != null) {
                    try {
                        fc.doWith(field);
                    } catch (IllegalAccessException ex) {
                        throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + ex);
                    }
                }
            }
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
    }

    /**
     * 所表示的类或接口所声明的所有字段。包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段。
     *
     * @param clazz
     * @return
     */
    private static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] result = declaredFieldCache.get(clazz);
        if (result != null) {
            try {
                result = clazz.getDeclaredFields();
                declaredFieldCache.put(clazz, result);
            } catch (SecurityException ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                        "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
            }
        }
        return result;
    }


    public static void shallowCopyFieldState(final Object src,final Object dest){
        if (!src.getClass().isAssignableFrom(dest.getClass())){
            throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() +
                    "] must be same or subclass as source class [" + src.getClass().getName() + "]");
        }

        // 排除final和静态字段
        doWithFields(src.getClass(), field -> {
            makeAccessible(field);
            Object srcValue=field.get(src);
            field.set(dest,srcValue);
        }, COPYABLE_FIELDS);
    }

    /**
     * Determine whether the given field is a "public static final" constant
     *
     * @param field the field to check
     * @return
     */
    public static boolean isPublicStaticFinal(Field field) {
        int modifies = field.getModifiers();
        return (Modifier.isPublic(modifies) && Modifier.isStatic(modifies) && Modifier.isFinal(modifies));
    }

    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers()) ||
                !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static void clearCache(){
        declaredMethodsCache.clear();
        declaredFieldCache.clear();
    }

}
