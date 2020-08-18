package com.spring.beans.config;

import com.spring.beans.BeanMetadataElement;
import com.spring.core.lang.Nullable;
import com.spring.core.util.ObjectUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConstructorArgumentValues {

    private final Map<Integer, ValueHolder> indexdArgumentValues = new LinkedHashMap<>();
    private final List<ValueHolder> genericArgumentValues = new ArrayList<>();

    public ConstructorArgumentValues() {
    }

    public void clear() {
        this.indexdArgumentValues.clear();
        this.genericArgumentValues.clear();
    }

    public boolean isEmpty() {
        return this.indexdArgumentValues.isEmpty() && this.genericArgumentValues.isEmpty();
    }


    public static class ValueHolder implements BeanMetadataElement {
        private Object value;
        private String type;
        private String name;
        private Object source;

        private boolean converted = false;
        private Object convertedValue;

        public ValueHolder(Object value) {
            this.value = value;
        }

        public ValueHolder(Object value, String type) {
            this.value = value;
            this.type = type;
        }

        public ValueHolder(Object value, String type, String name) {
            this.value = value;
            this.type = type;
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }

        public synchronized boolean isConverted() {
            return converted;
        }

        public synchronized void getConvertedValue(@Nullable Object value) {
            this.converted = (value != null);
            convertedValue = value;
        }

        public synchronized Object getConvertedValue() {
            return convertedValue;
        }

        private boolean contentEquals(ValueHolder other) {
            return (this == other || (ObjectUtils.nullSafeEquals(this.value, other.value) && ObjectUtils.nullSafeEquals(this.type, other.type)));
        }

        private int contentHashCode() {
            return ObjectUtils.nullSafeHashCode(this.value) * 29 + ObjectUtils.nullSafeHashCode(this.type);
        }

        public ValueHolder copy() {
            ValueHolder copy = new ValueHolder(this.value, this.type, this.name);
            copy.setSource(this.source);
            return copy;
        }


    }

}
