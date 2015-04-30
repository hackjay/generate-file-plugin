package com.edwin.plugin.parser;

import com.edwin.plugin.Constant;
import com.edwin.plugin.exception.ConfigException;
import com.intellij.psi.xml.XmlTag;

import java.lang.reflect.Field;

/**
 * Created By User: edwin. Time: 15-4-22 14:06.
 */
public class TextTagParser extends AbstractTagParser {

    @Override
    public void parseTag(XmlTag xmlTag) throws ConfigException {

        String tagName = xmlTag.getName();

        try {

            Class<?> clazz = configuration.getClass();

            Field[] fields = configuration.getClass().getDeclaredFields();
            for (Field field : fields) {

                // 调用set方法注入值
                if (tagName.toLowerCase().equals(field.getName().toLowerCase())) {
                    String setMethodName = new StringBuffer("set").append(field.getName().substring(0, 1).toUpperCase()).append(field.getName().substring(1)).toString();
                    clazz.getMethod(setMethodName, String.class).invoke(configuration, xmlTag.getValue().getText());
                }
            }
        } catch (Exception e) {
            throw new ConfigException("解析配置文件" + Constant.CONFIGURATION_FILE_NAME + "出错", e);
        }
    }
}
