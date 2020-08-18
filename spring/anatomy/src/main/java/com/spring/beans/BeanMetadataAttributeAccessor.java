package com.spring.beans;

import com.spring.core.AttributeAccessor;
import com.spring.core.AttributeAccessorSupport;
import com.spring.core.lang.Nullable;

public class BeanMetadataAttributeAccessor extends AttributeAccessorSupport implements BeanMetadataElement {

    @Nullable
    private Object source;

    public void setSource(@Nullable Object source) {
        this.source = source;
    }

    @Nullable
    public Object getSource() {
        return source;
    }

    public BeanMetadataAttribute getBeanMetadataAttribute(String name) {
        return (BeanMetadataAttribute) super.getAttribute(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        super.setAttribute(name, new BeanMetadataAttribute(name, value));
    }

    @Override
    public Object getAttribute(String name) {
        BeanMetadataAttribute attribute = (BeanMetadataAttribute) super.getAttribute(name);
        return attribute != null ? attribute.getValue() : null;
    }
}

