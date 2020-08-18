package com.spring.beans;

import com.spring.core.lang.Nullable;
import com.spring.core.util.ObjectUtils;

public interface Mergeable {

    boolean isMergeEnable();

    Object merge(@Nullable Object parent);
}
