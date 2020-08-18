package com.spring.core;

import com.spring.core.lang.Nullable;
import com.spring.core.util.Assert;
import com.spring.core.util.StringUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AttributeAccessorSupport implements AttributeAccessor, Serializable {

    private final Map<String, Object> attributes = new LinkedHashMap<>();

    @Override
    public void setAttribute(String name, @Nullable Object value) {
        Assert.notNull(name, "Name must not be null");
        if (value != null) {
            attributes.put(name, value);
        } else {
            removeAttribute(name);
        }
    }

    @Override
    public Object getAttribute(String name) {
        Assert.notNull(name, "Name must not be null");
        return attributes.get(name);
    }

    @Override
    public Object removeAttribute(String name) {
        Assert.notNull(name, "Name must not be null");
        return attributes.remove(name);
    }

    @Override
    public boolean hasAttribute(String name) {
        Assert.notNull(name, "Name must not be null");
        return attributes.containsKey(name);
    }

    @Override
    public String[] attributeNames() {
        return StringUtils.toStringArray(attributes.keySet());
    }

    protected void copyAttributeFrom(AttributeAccessor source){
        Assert.notNull(source, "Source must not be null");
        String[] attributesName=source.attributeNames();
        for (String attributeName : attributesName) {
            setAttribute(attributeName,source.getAttribute(attributeName));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj || (obj instanceof AttributeAccessor &&
                this.attributes.equals(((AttributeAccessorSupport) obj).attributes)));
    }

    @Override
    public int hashCode() {
        return this.attributes.hashCode();
    }
}
