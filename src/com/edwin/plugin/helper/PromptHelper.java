package com.edwin.plugin.helper;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * 提示工具类 Created By User: edwin. Time: 15-4-22 11:21.
 */
public class PromptHelper {

    /**
     * 警告提示信息
     * 
     * @param project
     * @param content
     * @param title
     */
    public static void warn(Project project, String content, String title) {
        Messages.showMessageDialog(project, content, title, Messages.getWarningIcon());
    }

    /**
     * 错误信息
     *
     * @param project
     * @param content
     * @param title
     */
    public static void error(Project project, String content, String title) {
        Messages.showMessageDialog(project, content, title, Messages.getErrorIcon());
    }
}
