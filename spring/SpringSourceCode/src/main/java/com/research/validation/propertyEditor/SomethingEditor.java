package com.research.validation.propertyEditor;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

/**
 * 查看Controller演示效果
 */
public class SomethingEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        // 属性值
        Object value = getValue();
        if (value instanceof String) {
            setValue(new Something(text));
            return;
        }
        throw new java.lang.IllegalArgumentException(text);
    }

    @Override
    public String getAsText() {
        Something something = (Something) this.getValue();
        if (null != something) {
            return something.getText();
        } else {
            return "";
        }
    }
}
