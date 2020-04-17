**SpringBoot 2.x 第二十四篇：数据库管理与迁移（Liquibase）**

目前 Spring Boot 支持较好的两款工具分别是 flyway、liquibase，支持 sql script，在初始化数据源之后执行指定的脚本代码或者脚本文件，本章基于 Liquibase…

1、Liquibase
LiquiBase 是一个用于数据库重构和迁移的开源工具，通过 changelog文件 的形式记录数据库的变更，然后执行 changelog文件 中的修改，将数据库更新或回滚到一致的状态。

主要特点

支持几乎所有主流的数据库，如MySQL、PostgreSQL、Oracle、Sql Server、DB2等
支持多开发者的协作维护；
日志文件支持多种格式；如XML、YAML、SON、SQL等
支持多种运行方式；如命令行、Spring 集成、Maven 插件、Gradle 插件等
在平时开发中，无可避免测试库增加字段或者修改字段以及创建表之类的，环境切换的时候如果忘记修改数据库那么肯定会出现 不可描述的事情 ，这个时候不妨考虑考虑Liquibase。

官方文档：http://www.liquibase.org/documentation/index.html

2、本章目标
利用 Spring Boot 集成 Liquibase，避免因粗心大意导致环境迁移时缺少字段….

导入依赖
依赖 spring-boot-starter-jdbc 目的是为了让 liquibase 能够获得 datasource ，这里换成 mybatis、hibernate 等也是一样，主要偷懒不想写配置….
<dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
</dependency>

属性配置
只要依赖了 liquibase-core 默认可以不用做任何配置，但还是需要知道默认配置值是什么，这样方便定位和解决问题
# spring.liquibase.enabled=true
# spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.yaml
更多配置
spring.liquibase.change-log 配置文件的路径，默认值为 classpath:/db/changelog/db.changelog-master.yaml
spring.liquibase.check-change-log-location 检查 change log的位置是否存在，默认为true.
spring.liquibase.contexts 用逗号分隔的运行环境列表。
spring.liquibase.default-schema 默认数据库 schema
spring.liquibase.drop-first 是否先 drop schema（默认 false）
spring.liquibase.enabled 是否开启 liquibase（默认为 true）
spring.liquibase.password 数据库密码
spring.liquibase.url 要迁移的JDBC URL，如果没有指定的话，将使用配置的主数据源.
spring.liquibase.user 数据用户名
spring.liquibase.rollback-file 执行更新时写入回滚的 SQL文件

db.changelog-master.yaml

test1.sql

上面的yaml文件其实就是从下面的 XML 演变而来的，官方是支持 xml，yaml，json 三种格式，写法也比较简单
传送门（官方给出了三种写法格式，依样画葫芦就可以了）：http://www.liquibase.org/documentation/changes/sql_file.html
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd">
    <changeSet id="1" author="Levin">
        <sqlFile path="classpath:db/changelog/changelog/test1.sql"/>
    </changeSet>
</databaseChangeLog>