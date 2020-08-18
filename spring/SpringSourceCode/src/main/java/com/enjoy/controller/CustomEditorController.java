package com.enjoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

@Controller
@RequestMapping("/custom")
public class CustomEditorController {

    // 路径中字符串因为SomethingEditor属性编辑器能自动转换成Something对象
    @RequestMapping(value = "/test/{something}", method = RequestMethod.GET)
    @ResponseBody
    public Something testSomething(@PathVariable("something") Something something) {
        return something;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Something.class, new SomethingEditor());
    }

    public class Something {
        private String text;

        public Something(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "Something{" +
                    "text='" + text + '\'' +
                    '}';
        }
    }

    public class SomethingEditor extends PropertyEditorSupport {

        @Override
        public String getAsText() {
            Something something = (Something) this.getValue();
            if (null != something) {
                return something.getText();
            }
            return "";
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (StringUtils.hasText(text)) {
                setValue(new Something(text));
                return;
            } else {
                setValue(null);
            }
        }
    }
}
