package com.spring.beans;

import com.spring.core.lang.Nullable;

public interface BeanMetadataElement {
    @Nullable
    default Object getResource() {
        return null;
    }
}
