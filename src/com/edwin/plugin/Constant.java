package com.edwin.plugin;

/**
 * 常量类 Created By User: edwin. Time: 15-4-22 11:22.
 */
public class Constant {

    /** 配置文件name */
    public static final String CONFIGURATION_FILE_NAME = "generate-config-file.xml";

    /** 默认类的包名 */
    public static final String DEFAUL_ENTITY_PACKAGE   = "entity";

    /** 默认类的包名 */
    public static final String DEFAUL_DAO_PACKAGE      = "dao";

    /** 资源配置类文件 */
    public static final String RESOURCES               = "resources";

    /** sqlmap-config.xml */
    public static final String SQL_MAP_CONFIG_XML      = "sqlmap-config.xml";

    // sql mapper
    public static final String NAMESPACE               = "NAMESPACE";

    public static final String CLASSPATH               = "CLASSPATH";

    public static final String MAPPING                 = "MAPPING";

    public static final String SELECTID                = "SELECTID";

    public static final String SQL                     = "SQL";

    public static final String INSERTSQL               = "INSERTSQL";

    public static final String INSERTID                = "INSERTID";

    public static final String KEY                     = "KEY";

    // sql config
    public static final String MAPPERXML               = "MAPPERXML";

    public static final String SQLMAPTAG               = "sqlMap";

    public static final String SETTINGSTAG             = "settings";

    public static final String SQLMAPTAGELEMENT        = "<sqlMap />";

    public static final String RESOURCE_ATTRIBUTE      = "resource";

    // spring
    public static final String BEAN                    = "bean";

    public static final String CLASS_ATTTIBUTE         = "class";

    public static final String LIMIT_SQL_FACTORY_BEAN  = "com.dianping.avatar.dao.ibatis.spring.LimitSqlMapClientFactoryBean";

    public static final String IBATIS_DAO_BEAN         = "com.dianping.avatar.dao.ibatis.IBatisGenericDaoImpl";

    public static final String ID_ATTTIBUTE            = "id";

    public static final String PROPERTY                = "property";

    public static final String NAME_ATTTIBUTE          = "name";

    public static final String VALUE_ATTTIBUTE         = "value";

    public static final String PROXY_INTERFACES        = "proxyInterfaces";

    public static final String DAO_BEAN                = "<bean id=\"_daoName_\" parent=\"parentDao\">\n"
                                                         + "\t\t<property name=\"proxyInterfaces\"\n"
                                                         + "\t\t\tvalue=\"_dao_\" />\n"
                                                         + "\t\t<property name=\"target\">\n"
                                                         + "\t\t\t<bean parent=\"_realizeTarget_\">\n"
                                                         + "\t\t\t\t<constructor-arg value=\"_namespace_\" />\n"
                                                         + "\t\t\t</bean>\n" + "\t\t</property>\n" + "\t</bean>";

}
