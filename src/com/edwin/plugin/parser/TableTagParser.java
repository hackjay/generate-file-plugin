package com.edwin.plugin.parser;

import com.edwin.plugin.exception.ConfigException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.psi.xml.XmlTag;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created By User: edwin. Time: 15-4-22 14:45.
 */
public class TableTagParser extends AbstractTagParser {

    @Override
    public void parseTag(XmlTag xmlTag) throws ConfigException {

        String ddl = xmlTag.getValue().getText().trim().replaceAll("\n", "");
        if (Strings.isNullOrEmpty(ddl)) {
            throw new ConfigException("配置文件中没有表定义");
        }

        try {

            String ddlStatement = ddl.substring(0, ddl.indexOf("("));

            Pattern pattern = Pattern.compile("`[^`]+`");
            Matcher matcher = pattern.matcher(ddlStatement);
            if (matcher.find()) {
                String tableName = matcher.group(0).replaceAll("`", "");
                configuration.setTableName(tableName);
                configuration.setClassName(tableName.indexOf("_") >= 0 ? tableName.substring(tableName.indexOf("_") + 1,
                                                                                             tableName.length()) : tableName);
            }

            String fieldStatement = ddl.substring(ddl.indexOf("(") + 1, ddl.indexOf("PRIMARY")).trim();

            String[] lines = fieldStatement.split(",");
            Map<String, String> fieldMap = Maps.newHashMapWithExpectedSize(lines.length);
            Map<String, String> classFieldMap = Maps.newHashMapWithExpectedSize(lines.length);
            List<String> fieldList = Lists.newArrayList();
            for (String line : lines) {
                String[] blocks = line.trim().split("\\s");
                String field = blocks[0].replaceAll("`", "");
                String type = blocks[1].replaceAll("\\([^)]+\\)", "");
                if (Strings.isNullOrEmpty(field) || Strings.isNullOrEmpty(type)) {
                    throw new ConfigException("配置文件中表定义错误");
                }
                fieldMap.put(field, type);
                field = field.contains("ID") ? field.replaceFirst("ID", "Id") : field;
                field = field.substring(0, 1).toLowerCase() + field.substring(1, field.length());
                classFieldMap.put(field, type);
                fieldList.add(field);
            }
            configuration.setFieldMap(fieldMap);
            configuration.setFieldList(fieldList);
            configuration.setClassFieldMap(classFieldMap);
        } catch (Exception e) {
            throw new ConfigException("配置文件中表定义错误");
        }
    }
}
