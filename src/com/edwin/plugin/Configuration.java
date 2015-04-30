package com.edwin.plugin;

import com.edwin.plugin.exception.ConfigException;
import com.google.common.base.Strings;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created By User: edwin. Time: 15-4-22 13:50.
 */
public class Configuration implements Validate, Serializable {

    private static final long   serialVersionUID = 5977420843635379559L;

    /** 模块名 */
    private String              moduleName;

    /** 包名 */
    private String              packageName;

    /** 表名 */
    private String              tableName;

    /** 类名 */
    private String              className;

    /** 表字段 */
    private Map<String, String> fieldMap;

    /** 类字段 */
    private Map<String, String> classFieldMap;

    /** 类字段List（顺序） */
    private List<String>        fieldList;

    /** sql map xml相对路径 */
    private String              sqlMapRelativePath;

    /** spring dao xml相对路径 */
    private String              springDaoRelativePath;

    /** spring ibatis xml相对路径 */
    private String              springIbatisRelativePath;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Map<String, String> getClassFieldMap() {
        return classFieldMap;
    }

    public void setClassFieldMap(Map<String, String> classFieldMap) {
        this.classFieldMap = classFieldMap;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    public String getSqlMapRelativePath() {
        return sqlMapRelativePath;
    }

    public void setSqlMapRelativePath(String sqlMapRelativePath) {
        this.sqlMapRelativePath = sqlMapRelativePath;
    }

    public String getSpringDaoRelativePath() {
        return springDaoRelativePath;
    }

    public void setSpringDaoRelativePath(String springDaoRelativePath) {
        this.springDaoRelativePath = springDaoRelativePath;
    }

    public String getSpringIbatisRelativePath() {
        return springIbatisRelativePath;
    }

    public void setSpringIbatisRelativePath(String springIbatisRelativePath) {
        this.springIbatisRelativePath = springIbatisRelativePath;
    }

    @Override
    public void selfValidate() throws Exception {

        // 这里可以用反射检测字段
        if (Strings.isNullOrEmpty(moduleName)) {
            throw new ConfigException("无法从配置文件中解析出moduleName");
        }

        if (Strings.isNullOrEmpty(tableName)) {
            throw new ConfigException("无法从配置文件中解析出表名");
        }

        if (Strings.isNullOrEmpty(packageName)) {
            throw new ConfigException("无法从配置文件中解析出包名");
        }

        if (fieldMap == null || fieldMap.size() == 0) {
            throw new ConfigException("无法从配置文件中解析出表结构");
        }

        if (Strings.isNullOrEmpty(sqlMapRelativePath)) {
            throw new ConfigException("sqlMapRelativePath属性没有配置");
        }
    }
}
