package com.research.validation.propertyEditor;

import org.springframework.stereotype.Component;

@Component
public class Something {

    private String text;

    public Something() {
    }

    public Something(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
