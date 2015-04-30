package com.edwin.plugin.generator;

import com.edwin.plugin.Configuration;
import com.edwin.plugin.exception.BreakException;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementFactory;

/**
 * Created By User: edwin. Time: 15-4-22 18:53.
 */
public abstract class AbstractGenerator implements FileGenerator {

    protected Project           project;

    protected Configuration     configuration;

    protected Module            module;

    protected PsiElementFactory psiElementFactory;

    public AbstractGenerator(Configuration configuration, Project project) {
        this.configuration = configuration;
        this.project = project;
    }

    @Override
    public void generate() throws Exception {

        module = ModuleManager.getInstance(project).findModuleByName(configuration.getModuleName());
        if (module == null) {
            throw new BreakException(configuration.getModuleName() + "模块不存在");
        }

        psiElementFactory = PsiElementFactory.SERVICE.getInstance(project);

        generateFile();
    }

    public abstract void generateFile() throws Exception;

    @Override
    public void setConfiguration(Configuration configurtion) {
        this.configuration = configurtion;
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public Project getProject() {
        return this.project;
    }
}
