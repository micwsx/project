package com.spring.beans;

import com.spring.core.lang.Nullable;
import com.spring.core.util.ObjectUtils;
import com.spring.core.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class MutablePropertyValues implements PropertyValues, Serializable {

    private final List<PropertyValue> propertyValueList;

    @Nullable
    private Set<String> processedProperties;

    private volatile boolean converted = false;

    public MutablePropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }


    public MutablePropertyValues(@Nullable PropertyValues original) {
        if (original != null) {
            PropertyValue[] pvs = original.getPropertyValues();
            propertyValueList = new ArrayList<>(pvs.length);
            for (PropertyValue pv : pvs) {
                propertyValueList.add(new PropertyValue(pv));
            }
        } else {
            this.propertyValueList = new ArrayList<>(0);
        }
    }

    public MutablePropertyValues(@Nullable Map<?, ?> original) {
        if (original != null) {
            propertyValueList = new ArrayList<>(original.size());
            original.forEach((key, value) -> {
                this.propertyValueList.add(new PropertyValue(key.toString(), value));
            });
        } else {
            this.propertyValueList = new ArrayList<>(0);
        }
    }

    public MutablePropertyValues(@Nullable List<PropertyValue> propertyValues) {
        this.propertyValueList = (propertyValues != null ? propertyValues : new ArrayList<>(0));
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

    public int size() {
        return this.propertyValueList.size();
    }

    public MutablePropertyValues addPropertyValue(@Nullable PropertyValues other) {
        if (other != null) {
            for (PropertyValue propertyValue : other.getPropertyValues()) {
                this.propertyValueList.add(new PropertyValue(propertyValue));
            }
        }
        return this;
    }


    public MutablePropertyValues addPropertyValue(@Nullable Map<?, ?> other) {
        if (other != null) {
            other.forEach((key, value) -> {
                addPropertyValue(new PropertyValue(key.toString(), value));
            });
        }
        return this;
    }

    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue currentPv = this.propertyValueList.get(i);
            if (currentPv.getName().equals(pv.getName())) {
                pv = mergeIfRequired(pv, currentPv);
                setPropertyValueAt(pv, i);
                return this;
            }

        }
        this.propertyValueList.add(pv);
        return this;
    }

    public void addPropertyValue(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
    }


    public MutablePropertyValues add(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
        return this;
    }

    public void setPropertyValueAt(PropertyValue pv, int i) {
        this.propertyValueList.add(i, pv);
    }

    private PropertyValue mergeIfRequired(PropertyValue newPv, PropertyValue currentPv) {
        Object value = newPv.getValue();
        if (value instanceof Mergeable) {
            Mergeable mergeable = (Mergeable) value;
            if (mergeable.isMergeEnable()) {
                Object merged = mergeable.merge(currentPv.getValue());
                return new PropertyValue(newPv.getName(), merged);
            }
        }
        return newPv;
    }

    public void removePropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.remove(propertyValue);
    }

    public void removePropertyValue(String propertyName) {
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }


    @Override
    public Iterator<PropertyValue> iterator() {
        return Collections.unmodifiableList(this.propertyValueList).iterator();
    }

    @Override
    public Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(this.propertyValueList, 0);
    }

    @Override
    public Stream<PropertyValue> stream() {
        return this.propertyValueList.stream();
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyName.equals(propertyValue.getName())) {
                return propertyValue;
            }
        }
        return null;
    }

    @Nullable
    public Object get(String propertyName) {
        PropertyValue pv = getPropertyValue(propertyName);
        return (pv != null ? pv.getValue() : null);
    }

    //相对于old获取有修改的pv
    @Override
    public PropertyValues changesSince(PropertyValues old) {
        MutablePropertyValues changes = new MutablePropertyValues();
        if (old == this)
            return changes;
        // 如果当前pv与old比较有修改，添加当前pv.
        for (PropertyValue newPv : this.propertyValueList) {
            PropertyValue pvOld = old.getPropertyValue(newPv.getName());
            if (pvOld != null || !pvOld.equals(newPv)) {
                changes.addPropertyValue(newPv);
            }
        }
        return changes;
    }

    @Override
    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null || (this.processedProperties != null && this.propertyValueList.contains(propertyName));
    }

    public void registerProcessedProperty(String propertyName) {
        if (this.processedProperties == null) {
            this.processedProperties = new HashSet<>(4);
        }
        this.processedProperties.add(propertyName);
    }


    public void clearProcessedProperty(String propertyName){
        if (this.processedProperties!=null)
            this.propertyValueList.remove(propertyName);
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }

    @Override
    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }

    public boolean isConverted() {
        return this.converted;
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj || (obj instanceof MutablePropertyValues && this.propertyValueList.equals(((MutablePropertyValues) obj).propertyValueList)));
    }

    @Override
    public int hashCode() {
        return this.propertyValueList.hashCode();
    }

    @Override
    public String toString() {
        PropertyValue[] pvs = getPropertyValues();
        if (pvs.length > 0) {
            return "PropertyValues: length=" + pvs.length + "; " + StringUtils.arrayToDelimitedString(pvs, "; ");
        }
        return "PropertyValues: length=0";
    }


}
