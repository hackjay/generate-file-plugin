package com.edwin.plugin.parser;

import com.edwin.plugin.Configuration;

/**
 * Created By User: edwin. Time: 15-4-22 14:16.
 */
public abstract class AbstractTagParser implements TagParser {

    protected Configuration configuration;

    @Override
    public void setConfiguration(Configuration configurtion) {
        this.configuration = configurtion;
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }
}
