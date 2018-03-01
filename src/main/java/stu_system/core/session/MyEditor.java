package stu_system.core.session;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import tools.FormatEmpty;

public class MyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (FormatEmpty.isEmpty(text))
            text = "0";
        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            setValue(Integer.parseInt(text));// 这句话是�?重要的，他的目的是�?�过传入参数的类型来匹配相应的databind
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        return getValue().toString();
    }
}
