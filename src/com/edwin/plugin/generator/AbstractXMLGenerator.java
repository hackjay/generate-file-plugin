package com.edwin.plugin.generator;

import com.edwin.plugin.Configuration;
import com.edwin.plugin.Constant;
import com.edwin.plugin.exception.BreakException;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Created By User: edwin. Time: 15-4-28 13:56.
 */
public abstract class AbstractXMLGenerator extends AbstractGenerator {

    protected VirtualFile  resourceRoot;

    protected FileTemplate sqlMapTemplate    = FileTemplateManager.getInstance().getJ2eeTemplate("SqlMapTemplate.xml");

    protected FileTemplate sqlConfigTemplate = FileTemplateManager.getInstance().getJ2eeTemplate("SqlConfigTemplate.xml");

    protected FileTemplate springIbatisTemplate    = FileTemplateManager.getInstance().getJ2eeTemplate("SpingIbatisTemplate.xml");

    protected FileTemplate springDaoTemplate    = FileTemplateManager.getInstance().getJ2eeTemplate("SpringDaoTemplate.xml");

    public AbstractXMLGenerator(Configuration configuration, Project project) {
        super(configuration, project);
    }

    @Override
    public void generateFile() throws Exception {

        // 获得 resource root of module
        VirtualFile[] virtualFiles = ModuleRootManager.getInstance(module).getSourceRoots();
        for (VirtualFile virtualFile : virtualFiles) {
            if (virtualFile.getName().equalsIgnoreCase(Constant.RESOURCES)) {
                resourceRoot = virtualFile;
                break;
            }
        }

        if (resourceRoot == null) {
            throw new BreakException("该项目没有resources目录");
        }

        generateXML();
    }

    public abstract void generateXML() throws Exception;
}
