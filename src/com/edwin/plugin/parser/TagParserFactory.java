package com.edwin.plugin.parser;

import com.edwin.plugin.TagTypeEnum;
import com.google.common.base.Strings;
import com.intellij.psi.xml.XmlTag;

/**
 * Created By User: edwin. Time: 15-4-22 14:46.
 */
public class TagParserFactory {

    /**
     * 获取tag解析器
     * 
     * @param xmlTag
     * @return
     */
    public static TagParser getTagParser(XmlTag xmlTag) {

        TagParser tagParser;

        String attType = xmlTag.getAttributeValue("type");
        if (!Strings.isNullOrEmpty(attType) && attType.toLowerCase().equals(TagTypeEnum.TABLE.toString().toLowerCase())) {
            tagParser = new TableTagParser();
        } else {
            tagParser = new TextTagParser();
        }

        return tagParser;
    }
}
