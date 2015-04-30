package com.edwin.plugin;

import com.edwin.plugin.exception.ConfigException;
import com.edwin.plugin.helper.PromptHelper;
import com.edwin.plugin.parser.TagParser;
import com.edwin.plugin.parser.TagParserFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.apache.commons.lang.ArrayUtils;

/**
 * Created By User: edwin. Time: 15-4-19 15:06.
 */
public class GenerateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        final Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);

        final Configuration configuration = new Configuration();

        try {

            PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, Constant.CONFIGURATION_FILE_NAME,
                                                              GlobalSearchScope.projectScope(project));
            if (ArrayUtils.isEmpty(psiFiles)) {
                throw new ConfigException("当前project中未发现配置文件：" + Constant.CONFIGURATION_FILE_NAME);
            }

            XmlFile xmlFile = (XmlFile) psiFiles[0];
            XmlTag[] tags = xmlFile.getRootTag().getSubTags();
            if (ArrayUtils.isEmpty(tags)) {
                throw new ConfigException("配置文件：" + Constant.CONFIGURATION_FILE_NAME + "内容为空");
            }

            // 解析配置文件
            for (XmlTag xmlTag : tags) {
                TagParser tagParser = TagParserFactory.getTagParser(xmlTag);
                tagParser.setConfiguration(configuration);
                tagParser.parseTag(xmlTag);
            }

            // 解析结果自检
            configuration.selfValidate();
        } catch (ConfigException e1) {
            PromptHelper.warn(project, e1.getMessage(), "配置解析异常");
        } catch (Exception e2) {
            PromptHelper.error(project, "少年，插件出错啦！", "插件异常");
        }

        Application application = ApplicationManager.getApplication();

        application.runWriteAction(new Runnable() {

            @Override
            public void run() {

                // 这个问题找了3天，intellij写入或修改文件需要特定的action或使用CommandProcessor
                CommandProcessor.getInstance().executeCommand(project, new GenerateTask(project, configuration),
                                                              "generate", null);
            }
        });
    }
}
