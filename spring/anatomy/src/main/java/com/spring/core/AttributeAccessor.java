package com.spring.core;


import com.spring.core.lang.Nullable;

public interface AttributeAccessor {
    void setAttribute(String name, @Nullable Object value);

    @Nullable
    Object getAttribute(String name);

    @Nullable
    Object removeAttribute(String name );

    boolean hasAttribute(String name);

    String[] attributeNames();


}
