package com.edwin.plugin;

import com.edwin.plugin.exception.ConfigException;

/**
 * Created By User: edwin. Time: 15-4-22 18:15.
 */
public interface Validate {

    /**
     * 对象自检测
     * 
     * @throws ConfigException
     */
    public void selfValidate() throws Exception;
}
