package com.edwin.plugin.generator;

import com.edwin.plugin.Configuration;
import com.edwin.plugin.exception.BreakException;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import org.apache.commons.lang.StringUtils;

/**
 * Created By User: edwin. Time: 15-4-23 9:36.
 */
public abstract class AbstractJavaClassGenerator extends AbstractGenerator {

    protected JavaPsiFacade    javaPsiFacade;

    protected PsiPackage       basePackage;

    protected PsiClass         psiClass;

    protected PsiReferenceList psiExtendsReferenceList;

    public AbstractJavaClassGenerator(Configuration configuration, Project project) {
        super(configuration, project);
    }

    @Override
    public void generateFile() throws Exception {

        // 前置检查
        preCheck();

        // 生成类文件
        generateClassFile();

        // 修改类文件
        modifyClassFile();

        // 格式化类文件
        formatClassFile();
    }

    /**
     * 前置检查
     * 
     * @throws Exception
     */
    public void preCheck() throws Exception {

        javaPsiFacade = javaPsiFacade == null ? JavaPsiFacade.getInstance(project) : javaPsiFacade;

        basePackage = basePackage == null ? javaPsiFacade.findPackage(configuration.getPackageName()) : basePackage;

        if (basePackage == null) {
            throw new BreakException("base包" + configuration.getPackageName() + "不存在");
        }
    }

    /**
     * 修改类文件
     */
    public void modifyClassFile() throws Exception {

        addReferenceList();

        addFields();

        addMethods();

        addImportList();
    }

    /**
     * 添加继承或实现类
     */
    protected abstract void addReferenceList() throws Exception;

    /**
     * 添加字段
     */
    protected abstract void addFields() throws Exception;

    /**
     * 添加方法
     */
    protected abstract void addMethods() throws Exception;

    /**
     * 添加import类
     */
    protected abstract void addImportList() throws Exception;

    /**
     * 生成类文件
     * 
     * @return
     * @throws Exception
     */
    protected abstract void generateClassFile() throws Exception;

    /**
     * 格式化类文件
     */
    protected void formatClassFile() {
        CodeStyleManager.getInstance(project).reformat(psiClass);
    }

    protected PsiType getPsiType(String type) {

        if (StringUtils.equalsIgnoreCase(type, "varchar") || StringUtils.equalsIgnoreCase(type, "mediumtext")) {
            return PsiType.getJavaLangString(PsiManager.getInstance(project), GlobalSearchScope.allScope(project));
        } else if (StringUtils.equalsIgnoreCase(type, "int") || StringUtils.equalsIgnoreCase(type, "tinyint")
                   || StringUtils.equalsIgnoreCase(type, "smallint")) {
            return PsiType.INT;
        } else if (StringUtils.equalsIgnoreCase(type, "boolean")) {
            return PsiType.BOOLEAN;
        } else if (StringUtils.equalsIgnoreCase(type, "datetime") || StringUtils.equalsIgnoreCase(type, "timestamp")) {
            return psiElementFactory.createType(javaPsiFacade.findClass("java.util.Date",
                                                                        GlobalSearchScope.allScope(project)));
        } else if (StringUtils.equalsIgnoreCase(type, "double")) {
            return PsiType.DOUBLE;
        }

        return null;
    }
}
