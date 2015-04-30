package com.edwin.plugin.generator;

import com.edwin.plugin.Configuration;
import com.intellij.openapi.project.Project;

/**
 * Created By User: edwin. Time: 15-4-22 18:55.
 */
public interface FileGenerator {

    /**
     * 生成方法
     */
    public void generate() throws Exception;

    /**
     * 设置configuration
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

    /**
     * 设置project
     * 
     * @param project
     */
    public void setProject(Project project);

    /**
     * 获取project
     * 
     * @return
     */
    public Project getProject();
}
