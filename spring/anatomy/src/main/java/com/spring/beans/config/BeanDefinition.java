package com.spring.beans.config;

import com.spring.beans.BeanMetadataElement;
import com.spring.beans.MutablePropertyValues;
import com.spring.core.AttributeAccessor;
import com.spring.core.ResolvableType;
import com.spring.core.lang.Nullable;

import javax.swing.*;

public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    int ROLE_APPLICATION = 0;
    int ROLE_SUPPORT = 1;
    int ROLE_INFRASTRUCTURE = 2;

    void setParentName(@Nullable String parentName);

    @Nullable
    String getParentName();

    void setBeanClassName(@Nullable String beanClassName);

    @Nullable
    String getBeanClassName();

    void setScope(@Nullable String scope);

    @Nullable
    String getScope();

    void setLazyInit(boolean lazyInit);

    boolean isLazyInit();

    void setDependsOn(@Nullable String... dependsOn);

    @Nullable
    String[] getDependsOn();

    void setAutowireCandidate(boolean autowireCandidate);

    boolean isAutowireCandidate();

    void setPrimary(boolean primary);

    boolean isPrimary();

    void setFactoryBeanName(@Nullable String factoryBeanName);

    String getFactoryBeanName();

    void setFactoryMethodName(@Nullable String factoryMethodName);

    String getFactoryMethodName();

    ConstructorArgumentValues getConstructorArgumentValues();

    default boolean hasConstructorArgumentValues() {
        return !getConstructorArgumentValues().isEmpty();
    }

    MutablePropertyValues getPropertyValues();

    default boolean hasPropertyValues(){return !getPropertyValues().isEmpty();}

    void setInitMethodName(@Nullable String initMethodName);

    @Nullable
    String getInitMethodName();

    void setDestroyMethodName(String destroyMethodName);

    @Nullable
    String getDestroyMethodName();

    void setRole(int role);

    int getRole();

    void setDescription(@Nullable String description);
    @Nullable
    String getDescription();

    ResolvableType getResolvableType();

    boolean isSingleton();

    boolean isPrototype();

    boolean isAbstract();

    @Nullable
    String getResourceDescription();

    @Nullable
    BeanDefinition getOriginatingBeanDefinition();

}
