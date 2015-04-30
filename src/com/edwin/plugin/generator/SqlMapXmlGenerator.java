package com.edwin.plugin.generator;

import com.edwin.plugin.Configuration;
import com.edwin.plugin.Constant;
import com.edwin.plugin.exception.BreakException;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.XmlElementFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * Created By User: edwin. Time: 15-4-27 19:53.
 */
public class SqlMapXmlGenerator extends AbstractXMLGenerator {

    public SqlMapXmlGenerator(Configuration configuration, Project project) {
        super(configuration, project);
    }

    @Override
    public void generateXML() throws Exception {

        VirtualFile sqlMapVirtualFile = resourceRoot.findFileByRelativePath(configuration.getSqlMapRelativePath());
        if (sqlMapVirtualFile == null) {
            throw new BreakException("根目录resources下" + configuration.getSqlMapRelativePath() + "目录不存在");
        }

        if (!sqlMapVirtualFile.isDirectory()) {
            throw new BreakException(configuration.getSqlMapRelativePath() + "不是一个文件目录");
        }

        PsiDirectory sqlMapDirectory = PsiManager.getInstance(project).findDirectory(sqlMapVirtualFile);

        generateMapperXML(sqlMapVirtualFile, sqlMapDirectory);

        generateOrModifyConfigXML(sqlMapVirtualFile, sqlMapDirectory);
    }

    /**
     * 生成获修改sqlmap-config.xml
     * 
     * @param sqlMapVirtualFile
     * @param sqlMapDirectory
     * @throws Exception
     */
    private void generateOrModifyConfigXML(VirtualFile sqlMapVirtualFile, PsiDirectory sqlMapDirectory)
                                                                                                       throws Exception {

        VirtualFile configXML = sqlMapVirtualFile.findChild(Constant.SQL_MAP_CONFIG_XML);

        String mapperPath = configuration.getSqlMapRelativePath();
        mapperPath = mapperPath.endsWith("/") ? mapperPath : (mapperPath + "/");
        mapperPath = mapperPath + configuration.getClassName() + ".xml";

        if (configXML == null) {
            Properties properties = new Properties();
            properties.put(Constant.MAPPERXML, mapperPath);
            FileTemplateUtil.createFromTemplate(sqlConfigTemplate, Constant.SQL_MAP_CONFIG_XML, properties,
                                                sqlMapDirectory);
        } else {
            modifyCongigXMl(configXML, mapperPath);
        }
    }

    private void modifyCongigXMl(VirtualFile configXML, String mapperPath) throws Exception {

        try {

            XmlFile xmlFile = (XmlFile) PsiManager.getInstance(project).findFile(configXML);

            XmlTag rootTag = xmlFile.getRootTag();
            XmlTag[] sqlMapTags = rootTag.findSubTags(Constant.SQLMAPTAG);

            if (!ArrayUtils.isEmpty(sqlMapTags)) {
                for (XmlTag sqlTag : sqlMapTags) {
                    String value = sqlTag.getAttributeValue(Constant.RESOURCE_ATTRIBUTE);
                    if (value.equalsIgnoreCase(mapperPath)) {
                        return;
                    }
                }
            }

            XmlElementFactory xmlElementFactory = XmlElementFactory.getInstance(project);
            XmlTag sqlMapTag = xmlElementFactory.createTagFromText(Constant.SQLMAPTAGELEMENT);
            sqlMapTag.setAttribute(Constant.RESOURCE_ATTRIBUTE, mapperPath);
            rootTag.addAfter(sqlMapTag, rootTag.findSubTags(Constant.SETTINGSTAG)[0]);

            CodeStyleManager.getInstance(project).reformat(xmlFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BreakException(Constant.SQL_MAP_CONFIG_XML + "文件格式错误", e);
        }
    }

    /**
     * 生成类似XX.xml
     * 
     * @param sqlMapVirtualFile
     * @param sqlMapDirectory
     * @throws Exception
     */
    private void generateMapperXML(VirtualFile sqlMapVirtualFile, PsiDirectory sqlMapDirectory) throws Exception {

        String tagetMapXMLName = configuration.getClassName() + ".xml";
        if (sqlMapVirtualFile.findChild(tagetMapXMLName) != null) {
            return;
        }

        FileTemplateUtil.createFromTemplate(sqlMapTemplate, tagetMapXMLName, buildMapperProperties(), sqlMapDirectory);
    }

    private Properties buildMapperProperties() {

        Properties properties = new Properties();

        properties.put(Constant.NAMESPACE, configuration.getClassName());
        properties.put(Constant.CLASSPATH, configuration.getPackageName() + ".entity." + configuration.getClassName()
                                           + "Entity");
        properties.put(Constant.SELECTID, "load" + StringUtils.capitalize(configuration.getClassName()));

        buildMappingSQL(properties);

        return properties;
    }

    private void buildMappingSQL(Properties properties) {

        List<String> fieldList = configuration.getFieldList();

        String firstDBField = null;
        String firstField = null;

        StringBuffer mapping = new StringBuffer();
        StringBuffer selectSql = new StringBuffer();
        StringBuffer insertSql = new StringBuffer();
        StringBuffer parameters = new StringBuffer();
        selectSql.append("SELECT\r\t\t\t");
        insertSql.append("INSERT INTO\r\t\t\t");
        insertSql.append(configuration.getTableName() + "\r\t\t(\r\t\t\t");
        for (int i = 0; i < fieldList.size(); i++) {
            String field = fieldList.get(i);
            String dbField = StringUtils.capitalize(field.contains("Id") ? field.replaceFirst("Id", "ID") : field).trim();
            if (i == 0) {
                firstDBField = dbField;
                firstField = field;
            }
            mapping.append("<result column=\"");
            mapping.append(dbField);
            mapping.append("\" property=\"");
            mapping.append(field);
            mapping.append("\"/>\n");
            selectSql.append(dbField);
            insertSql.append(dbField);
            parameters.append("#entity." + field + "#");
            if (i < fieldList.size() - 1) {
                selectSql.append(",\r\t\t\t");
                insertSql.append(",\r\t\t\t");
                parameters.append(",\r\t\t\t");
            }
        }
        selectSql.append("\r\t\tFROM\r\t\t\t" + configuration.getTableName());
        selectSql.append("\r\t\tWHERE\r\t\t\t" + firstDBField + " = " + "#" + firstField + "#");

        insertSql.append("\r\t\t)\r\t\tVALUES\r\t\t(\r\t\t\t" + parameters + "\r\t\t)");

        properties.setProperty(Constant.MAPPING, mapping.toString().trim());

        properties.setProperty(Constant.SQL, selectSql.toString());

        properties.setProperty(Constant.INSERTSQL, insertSql.toString());

        properties.setProperty(Constant.INSERTID, "add" + configuration.getClassName());

        properties.setProperty(Constant.KEY, firstDBField);
    }
}
