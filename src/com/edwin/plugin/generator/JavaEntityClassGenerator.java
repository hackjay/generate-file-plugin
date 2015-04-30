package com.edwin.plugin.generator;

import com.edwin.plugin.Configuration;
import com.edwin.plugin.Constant;
import com.edwin.plugin.exception.GenerateException;
import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created By User: edwin. Time: 15-4-23 11:09.
 */
public class JavaEntityClassGenerator extends AbstractJavaClassGenerator {

    public JavaEntityClassGenerator(Configuration configuration, Project project) {
        super(configuration, project);
    }

    @Override
    public void generateClassFile() throws Exception {

        String entityPackageName = configuration.getPackageName() + "." + Constant.DEFAUL_ENTITY_PACKAGE;

        // 创建包entity-if not exsit
        PsiDirectory psiDirectory = PackageUtil.findOrCreateDirectoryForPackage(module, entityPackageName, null, false);

        try {
            psiClass = JavaDirectoryService.getInstance().createClass(psiDirectory,
                                                                      configuration.getClassName() + "Entity");
        } catch (Exception e) {
            throw new GenerateException("Entity类已经存在", e);
        }
    }

    @Override
    protected void addReferenceList() {
    }

    @Override
    protected void addFields() {

        List<String> fieldList = configuration.getFieldList();

        for (String fieldName : fieldList) {
            PsiField psiField = psiElementFactory.createField(fieldName,
                                                              getPsiType(configuration.getClassFieldMap().get(fieldName)));
            psiClass.add(psiField);
        }
    }

    @Override
    protected void addMethods() {

        List<String> fieldList = configuration.getFieldList();

        for (String fieldName : fieldList) {
            PsiMethod getter = generateGetter(fieldName, getPsiType(configuration.getClassFieldMap().get(fieldName)));
            PsiMethod setter = generateSetter(fieldName, getPsiType(configuration.getClassFieldMap().get(fieldName)));
            psiClass.add(getter);
            psiClass.add(setter);
        }
    }

    @Override
    protected void addImportList() {

    }

    private PsiMethod generateGetter(String name, PsiType type) {

        PsiStatement returnStatement = psiElementFactory.createStatementFromText("return this." + name + ";", null);

        PsiMethod psiMethod = psiElementFactory.createMethod("get" + StringUtils.capitalize(name), type);

        psiMethod.getBody().add(returnStatement);

        return psiMethod;
    }

    private PsiMethod generateSetter(String name, PsiType type) {

        PsiParameter parameter = psiElementFactory.createParameter(name, type);

        PsiStatement setStatement = psiElementFactory.createStatementFromText("this." + name + "=" + name + ";", null);

        PsiMethod psiMethod = psiElementFactory.createMethod("set" + StringUtils.capitalize(name), PsiType.VOID);

        psiMethod.getBody().add(setStatement);

        psiMethod.getParameterList().add(parameter);

        return psiMethod;
    }
}
