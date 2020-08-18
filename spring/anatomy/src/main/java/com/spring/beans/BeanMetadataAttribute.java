package com.spring.beans;

import com.spring.core.lang.Nullable;
import com.spring.core.util.ObjectUtils;

public class BeanMetadataAttribute implements BeanMetadataElement {
    private final String name;
    @Nullable
    private final Object value;
    @Nullable
    private Object source;

    public BeanMetadataAttribute(String name, @Nullable Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public Object getValue() {
        return value;
    }

    public void setSource(@Nullable Object source) {
        this.source = source;
    }

    @Override
    public Object getResource() {
        return this.source;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof BeanMetadataElement))
            return  false;
        BeanMetadataAttribute otherMe=(BeanMetadataAttribute) obj;
        return this.name.equals(otherMe.getName())&&
                ObjectUtils.nullSafeEquals(this.value,otherMe.value)&&
                ObjectUtils.nullSafeEquals(this.source,otherMe.source);

    }

    @Override
    public String toString() {
        return "metadata attribute '" + this.name + "'";
    }
}
