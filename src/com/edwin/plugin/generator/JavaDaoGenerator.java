package com.edwin.plugin.generator;

import com.edwin.plugin.Configuration;
import com.edwin.plugin.Constant;
import com.edwin.plugin.exception.BreakException;
import com.edwin.plugin.exception.GenerateException;
import com.intellij.ide.util.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;

/**
 * Created By User: edwin. Time: 15-4-27 12:49.
 */
public class JavaDaoGenerator extends AbstractJavaClassGenerator {

    public JavaDaoGenerator(Configuration configuration, Project project) {
        super(configuration, project);
    }

    @Override
    protected void generateClassFile() throws Exception {

        String daoPackageName = configuration.getPackageName() + "." + Constant.DEFAUL_DAO_PACKAGE;

        PsiDirectory psiDirectory = PackageUtil.findOrCreateDirectoryForPackage(module, daoPackageName, null, false);

        try {
            psiClass = JavaDirectoryService.getInstance().createInterface(psiDirectory,
                                                                          configuration.getClassName() + "Dao");
        } catch (Exception e) {
            throw new GenerateException("Dao接口已经存在", e);
        }
    }

    @Override
    protected void addReferenceList() {

        psiExtendsReferenceList = psiClass.getExtendsList();

        PsiClass genericDao = javaPsiFacade.findClass("com.dianping.avatar.dao.GenericDao",
                                                      GlobalSearchScope.allScope(project));
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = psiElementFactory.createClassReferenceElement(genericDao);

        psiExtendsReferenceList.add(psiJavaCodeReferenceElement);
    }

    @Override
    protected void addFields() {
    }

    @Override
    protected void addMethods() {

        String primaryField = configuration.getFieldList().get(0);

        String selectMethod = "@DAOAction(action = DAOActionType.LOAD)public " + configuration.getClassName()
                              + "Entity " + "load" + configuration.getClassName() + "(@DAOParam(\"" + primaryField
                              + "\")int " + primaryField + ");";

        PsiMethod psiSelectMethod = psiElementFactory.createMethodFromText(selectMethod, null);

        psiClass.add(psiSelectMethod);

        String insertMethod = "@DAOAction(action = DAOActionType.INSERT)public Integer " + "add"
                              + configuration.getClassName() + "(@DAOParam(\"entity\")" + configuration.getClassName()
                              + "Entity entity);";

        PsiMethod psiInsertMethod = psiElementFactory.createMethodFromText(insertMethod, null);

        psiClass.add(psiInsertMethod);
    }

    @Override
    protected void addImportList() throws Exception {

        String className = configuration.getPackageName() + "." + Constant.DEFAUL_ENTITY_PACKAGE + "."
                           + configuration.getClassName() + "Entity";

        PsiClass entity = javaPsiFacade.findClass(className, GlobalSearchScope.moduleScope(module));
        if (entity == null) {
            return;
        }

        PsiJavaFile psiJavaFile = (PsiJavaFile) psiClass.getContainingFile();

        try {
            PsiClass daoAction = javaPsiFacade.findClass("com.dianping.avatar.dao.annotation.DAOAction",
                                                         GlobalSearchScope.allScope(project));
            PsiClass daoActionType = javaPsiFacade.findClass("com.dianping.avatar.dao.annotation.DAOActionType",
                                                             GlobalSearchScope.allScope(project));
            PsiClass daoParam = javaPsiFacade.findClass("com.dianping.avatar.dao.annotation.DAOParam",
                                                        GlobalSearchScope.allScope(project));
            psiJavaFile.getImportList().add(psiElementFactory.createImportStatement(entity));
            psiJavaFile.getImportList().add(psiElementFactory.createImportStatement(daoAction));
            psiJavaFile.getImportList().add(psiElementFactory.createImportStatement(daoActionType));
            psiJavaFile.getImportList().add(psiElementFactory.createImportStatement(daoParam));
        } catch (Exception e) {
            throw new BreakException("检查是否导入了avatar-dao包，dao生成需依赖该包");
        }
    }
}
