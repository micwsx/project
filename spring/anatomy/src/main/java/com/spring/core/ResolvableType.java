package com.spring.core;

import com.spring.core.lang.Nullable;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.*;

import com.spring.core.SerializableTypeWrapper.*;
import com.spring.core.util.ObjectUtils;
import org.springframework.lang.NonNullApi;
import org.springframework.util.ConcurrentReferenceHashMap;

public class ResolvableType implements Serializable {

    public static final ResolvableType NONE = new ResolvableType(EmptyType.INSTANCE, null, null, 0);

    private static final ResolvableType[] EMPTY_TYPES_ARRAY = new ResolvableType[0];

    private static final ConcurrentReferenceHashMap<ResolvableType, ResolvableType> cached = new ConcurrentReferenceHashMap<>(256);

    private final Type type;
    @Nullable
    private final TypeProvider typeProvider;

    @Nullable
    private final VariableResolver variableResolver;

    @Nullable
    private final ResolvableType componentType;

    @Nullable
    private final Integer hash;
    @Nullable
    private Class<?> resolved;
    @Nullable
    private volatile ResolvableType superType;
    @Nullable
    private volatile ResolvableType[] interfaces;
    @Nullable
    private volatile ResolvableType[] generics;

    public ResolvableType(Type type, @Nullable TypeProvider typeProvider, @Nullable VariableResolver variableResolver) {
        this.type = type;
        this.typeProvider = typeProvider;
        this.variableResolver = variableResolver;
        this.componentType = null;
        this.hash = calculateHashCode();
        this.resolved = null;
    }

    private ResolvableType(Type type, @Nullable TypeProvider typeProvider,
                           @Nullable VariableResolver variableResolver, @Nullable Integer hash) {
        this.type = type;
        this.typeProvider = typeProvider;
        this.variableResolver = variableResolver;
        this.componentType = null;
        this.hash = hash;
        this.resolved = resolveClass();
    }

    private ResolvableType(Type type, @Nullable TypeProvider typeProvider,
                           @Nullable VariableResolver variableResolver, @Nullable ResolvableType componentType) {

        this.type = type;
        this.typeProvider = typeProvider;
        this.variableResolver = variableResolver;
        this.componentType = componentType;
        this.hash = null;
        this.resolved = resolveClass();
    }

    private ResolvableType(@Nullable Class<?> clazz) {
        this.resolved = (clazz != null ? clazz : Object.class);
        this.type = this.resolved;
        this.typeProvider = null;
        this.variableResolver = null;
        this.componentType = null;
        this.hash = null;
    }

    public Type getType() {
        return SerializableTypeWrapper.unwrap(this.type);
    }

    public Class<?> getRawClass() {
        if (this.type == this.resolved) {
            return this.resolved;
        }
        Type rawType = this.type;
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }
        return rawType instanceof Class ? (Class<?>) rawType : null;
    }

    public Object getSource() {
        Object source = this.typeProvider != null ? this.typeProvider.getSource() : null;
        return source == null ? this.type : source;
    }

    public Class<?> toClass() {
        return resolve(Object.class);
    }


    @Nullable
    public Class<?> resolve(Class<?> fallback) {
        return this.resolved != null ? this.resolved : fallback;
    }

    @Nullable
    private Class<?> resolveClass() {
        if (this.type == EmptyType.INSTANCE)
            return null;
        if (this.type instanceof Class)
            return (Class<?>) this.type;
        if (this.type instanceof GenericArrayType) {
            Class<?> resolvedComponent = getComponentType().resolve();
            return (resolvedComponent != null ? Array.newInstance(resolvedComponent, 0).getClass() : null);
        }
        return resolveType().resolve();
    }

    public ResolvableType getComponentType() {
        if (this == NONE)
            return null;
        return null;
    }

    ResolvableType resolveType() {
        if (this.type instanceof ParameterizedType) {
            return null;
        }


        return null;
    }


    @Nullable
    public Class<?> resolve() {
        return this.resolved;
    }

    static ResolvableType forType(@Nullable Type type,@Nullable TypeProvider typeProvider,@Nullable VariableResolver variableResolver){
        if (type==null&&typeProvider!=null){
            type=SerializableTypeWrapper.forTypeProvider(typeProvider);
        }
        if (type==null){
            return NONE;
        }
        if (type instanceof Class){
            return new ResolvableType(type,typeProvider,variableResolver,(ResolvableType)null);
        }
        cached.purgeUnreferencedEntries();
        ResolvableType resultType=new ResolvableType(type,typeProvider,variableResolver);
        ResolvableType cachedType=cached.get(resultType);
        if (cachedType==null){
            cachedType=new ResolvableType(type,typeProvider,variableResolver,resultType.hash);
            cached.put(cachedType,cachedType);
        }
        resultType.resolved=cachedType.resolved;
        return resultType;
    }

    private int calculateHashCode() {
        int hashCode = ObjectUtils.nullSafeHashCode(this.type);
        if (this.typeProvider != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.typeProvider.getType());
        }
        if (this.variableResolver != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.variableResolver.getSource());
        }
        if (this.componentType != null) {
            hashCode = 31 * hashCode + ObjectUtils.nullSafeHashCode(this.componentType);
        }
        return hashCode;
    }

    @Nullable
    private ResolvableType resolveVariable(TypeVariable<?> variable) {

        return null;
    }


    interface VariableResolver extends Serializable {
        Object getSource();

        @Nullable
        ResolvableType resolveVariable(TypeVariable<?> variable);
    }

    private static class DefaultVariableResolver implements VariableResolver {

        private final ResolvableType source;

        public DefaultVariableResolver(ResolvableType source) {
            this.source = source;
        }

        @Override
        public Object getSource() {
            return this.source;
        }

        @Override
        public ResolvableType resolveVariable(TypeVariable<?> variable) {
            return this.source.resolveVariable(variable);
        }
    }

    static class EmptyType implements Type, Serializable {
        static final Type INSTANCE = new EmptyType();

        Object readResolve() {
            return INSTANCE;
        }
    }
}
