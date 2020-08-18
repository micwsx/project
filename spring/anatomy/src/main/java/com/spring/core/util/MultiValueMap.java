package com.spring.core.util;

import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Map;

public interface MultiValueMap<K, V> extends Map<K, List<V>> {

    V getFirst(K key);

    void add(K key, @Nullable V value);

    void addAll(MultiValueMap<K, V> values);

    void addAll(K key, List<? extends V> values);

    default void addIfAbsent(K key, @Nullable V value) {
        if (!containsKey(key)) {
            add(key, value);
        }
    }

    void set(K key, @Nullable V value);

    void setAll(Map<K, V> values);

    Map<K, V> toSingleValueMap();

}
