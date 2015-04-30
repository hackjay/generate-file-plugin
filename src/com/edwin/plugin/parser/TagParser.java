package com.edwin.plugin.parser;

import com.edwin.plugin.Configuration;
import com.edwin.plugin.exception.ConfigException;
import com.intellij.psi.xml.XmlTag;

/**
 * Created By User: edwin. Time: 15-4-22 14:04.
 */
public interface TagParser {

    /**
     * 解析tag
     * 
     * @param xmlTag
     * @return
     */
    public void parseTag(XmlTag xmlTag) throws ConfigException;

    /**
     * 设置configurtion
     * 
     * @param configuration
     */
    public void setConfiguration(Configuration configuration);

    /**
     * 获取configuration
     * 
     * @return
     */
    public Configuration getConfiguration();
}
