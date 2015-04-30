package com.edwin.plugin;

import com.edwin.plugin.exception.BreakException;
import com.edwin.plugin.generator.*;
import com.edwin.plugin.helper.PromptHelper;
import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;

import java.util.List;

/**
 * Created By User: edwin. Time: 15-4-19 15:57.
 */
public class GenerateTask implements Runnable {

    private Project       project;

    private Configuration configuration;

    public GenerateTask(Project project, Configuration configuration) {
        this.project = project;
        this.configuration = configuration;
    }

    @Override
    public void run() {

        List<FileGenerator> generators = Lists.newArrayList();

        FileGenerator entityGenerator = new JavaEntityClassGenerator(configuration, project);
        FileGenerator daoGenerator = new JavaDaoGenerator(configuration, project);
        FileGenerator xmlGenerator = new SqlMapXmlGenerator(configuration, project);
        FileGenerator springGenerator = new SpringDaoXMLGenerator(configuration, project);

        generators.add(entityGenerator);
        generators.add(daoGenerator);
        generators.add(xmlGenerator);
        generators.add(springGenerator);

        for (FileGenerator generator : generators) {
            try {
                generator.generate();
            } catch (BreakException e) {
                PromptHelper.error(project, e.getMessage(), "配置异常");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
