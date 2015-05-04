generate-file-plugin
=====

####简介

使用Ibatis时，经常会遇到建一张新表后需要配置table.xml、sqlmap-config.xml、entity、dao、spring-dao.xml等一些列文件，每次都手动去配置这些文件真的很烦，最重要的是这些配置文件都跟表结构相关且是重复工作，很显然可以通过系统自动来生成。鉴于公司大部分同事都是使用的idea做开发，所以开发了一个intellij插件，简单但实用

####难点

+  最大的难点在于intellij社区的不成熟，不像eclipse有强大的社区支持，只能在网上找一些英文资料（很多还是过期的），然后一次次尝试，其中有个文件不能写入的问题找了好久。

####使用介绍

+  在intellij里面点击install from disk，选择generate-file-plugin.jar插件安装

+  配置generate-file-plugin.xml文件，在一个project中只要配置一个

<!-- 该配置文件为project级，只需要在一个project窗口中建一个generate-config-file.xml文件，位置不限制 -->
<!-- type属性默认为text -->
<config>

    <!-- moduleName为模块名称 eg. activity-web -->
    <moduleName>Test3</moduleName>

    <!-- packageName为基础包 eg. com.dianping.activityweb -->
    <packageName>com.edwin.test</packageName>

    <!-- sqlMapRelativePath为sqlmap-config.xml和xx.xml所在目录相对resources目录的路径，用于生成XML eg. congfig/sqlmap/activity -->
    <sqlMapRelativePath>config/sqlmap/activity</sqlMapRelativePath>

    <!-- springDaoRelativePath为spring中配置dao bean的XML文件，是相对resources目录的路径 eg. congfig/spring/appcontext-dao-dianping.xml -->
    <springDaoRelativePath>config/spring/appcontext-dao-dianping.xml</springDaoRelativePath>

    <!-- springIbatisRelativePath为spring中配置ibatis bean的XML文件，是相对resources目录的路径 eg. congfig/spring/appcontext-db-dianping.xml -->
    <springIbatisRelativePath>config/spring/appcontext-db-dianping.xml</springIbatisRelativePath>

    <!-- table为数据库表，直接复制过来即可 eg. GP_EventFollowNote -->
    <table type="table">
        CREATE TABLE `GP_EventFollowNote` (
        `FollowNoteID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
        `EventID` int(11) NOT NULL COMMENT '活动ID',
        `UserID` int(11) NOT NULL COMMENT '用户ID',
        `NoteBody` varchar(600) NOT NULL DEFAULT '' COMMENT '回应内容',
        `AddDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
        `UpdateDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
        `IP` varchar(16) NOT NULL DEFAULT '0.0.0.0' COMMENT 'IP地址',
        `OrigUserID` int(11) NOT NULL COMMENT '活动发起人ID',
        `ToUserID` int(11) NOT NULL DEFAULT '0',
        `EventTitle` varchar(50) NOT NULL DEFAULT '' COMMENT '活动标题',
        `Status` tinyint(11) NOT NULL DEFAULT '3' COMMENT '初始状态/机器审核未通过(0)、机器审核通过（1）、人工审核通过（2）、人工审核删除（3）、用户删除（4） ',
        PRIMARY KEY (`FollowNoteID`),
        KEY `IX_EventID_Status_FollowNoteID` (`EventID`,`Status`,`FollowNoteID`),
        KEY `IX_UserID_Status` (`UserID`,`Status`),
        KEY `IX_AddDate` (`AddDate`)
        ) ENGINE=InnoDB AUTO_INCREMENT=7815655 DEFAULT CHARSET=utf8 COMMENT='社区活动回应表';
    </table>
</config>

