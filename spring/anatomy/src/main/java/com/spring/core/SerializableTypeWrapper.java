package com.spring.core;

import com.spring.core.lang.Nullable;
import com.spring.core.util.ReflectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ObjectUtils;

import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.*;

final class SerializableTypeWrapper {
    private static final Class<?>[] SUPPORTED_SERIALIZABLE_TYPES = new Class[]{GenericArrayType.class, ParameterizedType.class, TypeVariable.class, WildcardType.class};
    static final ConcurrentReferenceHashMap<Type, Type> cache = new ConcurrentReferenceHashMap<>(256);

    public SerializableTypeWrapper() {
    }

    @Nullable
    public static Type forField(Field field) {
        return forTypeProvider(new FieldTypeProvider(field));
    }

    @Nullable
    public static Type forMethodParameter(MethodParameter methodParameter){
        return forTypeProvider(new MethodParameterTypeProvider(methodParameter));
    }



    public static <T extends Type> T unwrap(T type) {
        Type unwrapped = null;
        if (type instanceof SerializableTypeProxy) {
            unwrapped = ((SerializableTypeProxy) type).getTypeProvider().getType();
        }
        return unwrapped != null ? (T) unwrapped : type;
    }

    static Type forTypeProvider(TypeProvider provider) {
        Type providedType = provider.getType();
        // 没有实现Serializable接口
        if (providedType != null && !(providedType instanceof Serializable)) {
            if (!GraalDetector.inImageCode() && Serializable.class.isAssignableFrom(Class.class)) {
                Type cached = cache.get(providedType);
                if (cached != null) {
                    return cached;
                } else {
                    Class[] supportedSerializableTypes = SUPPORTED_SERIALIZABLE_TYPES;
                    int length = supportedSerializableTypes.length;
                    for (int i = 0; i < length; ++i) {
                        Class<?> type = supportedSerializableTypes[i];
                        // 如果指定类型是兼容数组中的类型
                        if (type.isInstance(providedType)) {
                            ClassLoader classLoader = provider.getClass().getClassLoader();
                            Class<?>[] interfaces = new Class[]{type, SerializableTypeProxy.class, Serializable.class};
                            InvocationHandler handler = new TypeProxyInvocationHandler(provider);
                            cached = (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
                            cache.put(providedType, cached);
                            return cached;
                        }
                    }
                    throw new IllegalArgumentException("Unsupported Type class: " + providedType.getClass().getName());
                }
            } else {
                return providedType;
            }
        } else {
            return providedType;
        }
    }

    static class MethodInvokeTypeProvider implements SerializableTypeWrapper.TypeProvider {
        private final TypeProvider provider;
        private final String methodName;
        private final Class<?> declaringClass;
        private final int index;
        private transient Method method;
        @Nullable
        private transient volatile Object result;

        public MethodInvokeTypeProvider(TypeProvider provider, Method method, int index) {
            this.provider = provider;
            this.methodName = method.getName();
            this.declaringClass = method.getDeclaringClass();
            this.index = index;
            this.method = method;
        }

        @Override
        public Type getType() {
            Object result = this.result;
            if (result == null) {
                result= ReflectionUtils.invokeMethod(this.method, this.provider.getType());
                this.result=result;
            }
            return result instanceof Type[] ? ((Type[]) result)[index] : (Type) result;
        }

        @Override
        public Object getSource() {
            return null;
        }

        private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
            inputStream.defaultReadObject();
            Method method = ReflectionUtils.findMethod(this.declaringClass, this.methodName);
            if (method == null) {
                throw new IllegalStateException("Cannot find method on deserialization: " + this.methodName);
            } else if (method.getReturnType() != Type.class && method.getReturnType() != Type[].class) {
                throw new IllegalStateException("Invalid return type on deserialized method - needs to be Type or Type[]: " + method);
            } else {
                this.method = method;
            }
        }
    }

    static class MethodParameterTypeProvider implements TypeProvider{
        @Nullable
        private final String methodName;
        private final Class<?>[] parameterTypes;
        private final Class<?> declaringClass;
        private final int parameterIndex;
        private transient MethodParameter methodParameter;

        public MethodParameterTypeProvider(MethodParameter methodParameter) {
            this.methodName=methodParameter.getMethod()!=null?methodParameter.getMethod().getName():null;
            this.parameterTypes=methodParameter.getExecutable().getParameterTypes();
            this.declaringClass=methodParameter.getDeclaringClass();
            this.parameterIndex=methodParameter.getParameterIndex();
            this.methodParameter = methodParameter;
        }

        @Override
        public Type getType() {
            return this.methodParameter.getGenericParameterType();
        }

        @Override
        public Object getSource() {
            return this.methodParameter;
        }

        private void readObject(ObjectInputStream inputStream) throws IOException,ClassNotFoundException{
            inputStream.defaultReadObject();
            try {
                if (this.methodName!=null){
                    this.methodParameter=new MethodParameter(this.declaringClass.getDeclaredMethod(this.methodName, this.parameterTypes),this.parameterIndex);
                }else{
                    this.methodParameter=new MethodParameter(this.declaringClass.getDeclaredConstructor(this.parameterTypes),this.parameterIndex);
                }
            }catch (Throwable var3) {
                throw new IllegalStateException("Could not find original class structure", var3);
            }
        }
    }

    private static class TypeProxyInvocationHandler implements InvocationHandler, Serializable {
        private final TypeProvider provider;

        public TypeProxyInvocationHandler(TypeProvider provider) {
            this.provider = provider;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("equals") && args != null) {
                Object other = args[0];
                if (other instanceof Type) {
                    other = unwrap((Type) other);
                }
                return ObjectUtils.nullSafeEquals(this.provider.getType(), other);
            } else if (method.getName().equals("hashCode")) {
                return ObjectUtils.nullSafeHashCode(this.provider.getType());
            } else if (method.getName().equals("getTypeProvider")) {
                return this.provider;
            } else if (Type.class == method.getReturnType() && args == null) {
                return SerializableTypeWrapper.forTypeProvider(new MethodInvokeTypeProvider(this.provider,method,-1));
            } else if (Type[].class == method.getReturnType() && args == null) {
                Type[] result = new Type[((Type[]) method.invoke(this.provider.getType())).length];
                for (int i = 0; i < result.length; i++) {
                    result[i] = SerializableTypeWrapper.forTypeProvider(new MethodInvokeTypeProvider(this.provider, method, i));
                }
                return result;
            } else {
                try {
                    return method.invoke(this.provider.getType(), args);
                } catch (InvocationTargetException var6) {
                    throw var6.getTargetException();
                }
            }
        }
    }


    static class FieldTypeProvider implements TypeProvider {

        private final String fieldName;
        private final Class<?> declaringClass;
        private transient Field field;

        public FieldTypeProvider(Field field) {
            this.field = field;
            this.fieldName = field.getName();
            this.declaringClass = field.getDeclaringClass();
        }

        @Override
        public Type getType() {
            return this.field.getGenericType();
        }

        @Override
        public Object getSource() {
            return null;
        }

        private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
            inputStream.defaultReadObject();
            try {
                this.field = this.declaringClass.getDeclaredField(this.fieldName);
            } catch (Throwable e) {
                throw new IllegalStateException("Could not find original class structure", e);
            }
        }
    }

    interface TypeProvider extends Serializable {
        @Nullable
        Type getType();

        @Nullable
        default Object getSource() {
            return null;
        }
    }


    interface SerializableTypeProxy {
        TypeProvider getTypeProvider();
    }

}
