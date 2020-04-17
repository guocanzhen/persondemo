
相关链接：https://www.ycbbs.vip/?p=1306

**SpringBoot 2.x 第一篇：构建第一个 SpringBoot 工程**


**SpringBoot 2.x 第二篇：SpringBoot配置详解**
1、总结：掌握@ConfigurationProperties、@PropertySource 等注解的用法及作用
2、掌握编写自定义配置
3、掌握外部命令引导配置的方式：

在打开 cmd 程序，输入：java -jar chapter2-0.0.1-SNAPSHOT.jar --spring.profiles.active=test --my1.age=32。仔细观察spring.profiles.active=test、my1.age=32 这俩配置的键值是不是似曾相识（不认识的请从开头认真阅读）
最后输入测试地址：http://localhost:8080/test/properties/1 我们可以发现返回的JSON变成了 {"age":32,"name":"canzhen"} 表示正确



**SpringBoot 2.x 第四篇：整合Thymeleaf模板**

1、Thymeleaf是现代化服务器端的Java模板引擎，不同与其它几种模板的是Thymeleaf的语法更加接近HTML，并且具有很高的扩展性。详细资料可以浏览官网。
官网：https://www.thymeleaf.org/
2、Spring4.3以后为简化@RequestMapping(method = RequestMethod.XXX)的写法，故而将其做了一层包装，也就是现在的GetMapping、PostMapping、PutMapping、DeleteMapping、PatchMapping
3、模板热部署
在 IntelliJ IDEA 中使用 thymeleaf 模板的时候，发现每次修改静态页面都需要重启才生效，这点是很不友好的，百度了下发现原来是默认配置搞的鬼，为了提高响应速度，默认情况下会缓存模板。如果是在开发中请将spring.thymeleaf.cache 属性设置成 false。在每次修改静态内容时按Ctrl+Shift+F9即可重新加载了…
默认配置
SpringBoot 默认情况下为我们做了如下的默认配置工作，熟悉默认配置在开发过程中可以更好的解决问题




**SpringBoot 2.x 第五篇：使用 JdbcTemplate 访问数据库**
1、连接数据库
在application.properties中添加如下配置。值得注意的是，SpringBoot 默认会自动配置DataSource，它将优先采用HikariCP连接池，如果没有该依赖的情况则选取tomcat-jdbc，如果前两者都不可用最后选取Commons DBCP2。通过spring.datasource.type属性可以指定其它种类的连接池
spring.datasource.url=jdbc:mysql://localhost:3306/chapter4?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false
spring.datasource.password=root
spring.datasource.username=root
#spring.datasource.type
#更多细微的配置可以通过下列前缀进行调整
#spring.datasource.hikari
#spring.datasource.tomcat
#spring.datasource.dbcp2



**SpringBoot 2.x 第八篇：通用Mapper与分页插件的集成**

1、分页插件

GIT地址： https://github.com/pagehelper/Mybatis-PageHelper
在没有分页插件之前，写一个分页需要两条SQL语句，一条查询一条统计，然后才能计算出页码，这样的代码冗余而又枯燥，更重要的一点是数据库迁移，众所周知不同的数据库分页写法是不同的，而Mybatis不同于Hibernate的是它只提供动态SQL和结果集映射。值得庆幸的是，它虽然没有为分页提供良好的解决方案，但却提供了Interceptor以供开发者自己扩展，这也是这款分页插件的由来….

2、通用Mapper

GIT地址： https://gitee.com/free/Mapper
通用 Mapper 是一个可以实现任意 MyBatis 通用方法的框架，项目提供了常规的增删改查操作以及 Example 相关的单表操作。通用 Mapper 是为了解决 MyBatis 使用中 90% 的基本操作，使用它可以很方便的进行开发，可以节省开发人员大量的时间。

3、属性配置
在 application.properties 文件中分别添加上数据库、Mybatis、通用Mapper、PageHelper的属性配置，这里只提供了常见场景的配置，更全的配置可以参考上文所述的文文档(#^.^#)

通用Mapper

mapper.enum-as-simple-type： 枚举按简单类型处理，如果有枚举字段则需要加上该配置才会做映射
mapper.not-empty： 设置以后，会去判断 insert 和 update 中符串类型!="

分页插件

pagehelper.reasonable： 分页合理化参数，默认值为 false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。
support-methods-arguments： 支持通过 Mapper 接口参数来传递分页参数，默认值 false，分页插件会从查询方法的参数值中，自动根据上面 params 配置的字段中取值，查找到合适的值时就会自动分页。

4、实体类
通用Mapper采用了JPA规范包中的注解，这种的设计避免了重复造轮子，更是让Spring Data Jpa的应用可以轻松切换到Mybatis

5、持久层
继承 BaseMapper<T> 就可以了，这点是不是有点类似 JpaRepository，同时也可以根据自己需要扩展出更适合自己项目的BaseMapper，它的灵活也是众多开发者喜爱的因素之一

6、总结
Mybatis官方文档： http://www.mybatis.org/mybatis-3/zh/index.html
通用Mapper文档： https://gitee.com/free/Mapper/wikis/1.1-java?parent=1.integration
分页插件文档： https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md