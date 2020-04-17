**SpringBoot 2.x | 第十四篇：强大的 actuator 服务监控与管理**

actuator是spring boot项目中非常强大一个功能，有助于对应用程序进行监视和管理，通过 restful api 请求来监管、审计、收集应用的运行情况，针对微服务而言它是必不可少的一个环节…

1、Endpoints
actuator 的核心部分，它用来监视应用程序及交互，spring-boot-actuator中已经内置了非常多的 Endpoints（health、info、beans、httptrace、shutdown等等），同时也允许我们自己扩展自己的端点

Spring Boot 2.0 中的端点和之前的版本有较大不同,使用时需注意。另外端点的监控机制也有很大不同，启用了不代表可以直接访问，还需要将其暴露出来，传统的management.security管理已被标记为不推荐。

2、内置Endpoints
    id	            desc	                                                Sensitive
auditevents	    显示当前应用程序的审计事件信息	                                Yes
beans	        显示应用Spring Beans的完整列表	                            Yes
caches	        显示可用缓存信息	                                            Yes
conditions	    显示自动装配类的状态及及应用信息	                            Yes
configprops	    显示所有 @ConfigurationProperties 列表	                    Yes
env	            显示 ConfigurableEnvironment 中的属性	                    Yes
flyway	        显示 Flyway 数据库迁移信息	                                Yes
health	        显示应用的健康信息（未认证只显示status，认证显示全部信息详情）	No
info	        显示任意的应用信息（在资源文件写info.xxx即可）	                No
liquibase	    展示Liquibase 数据库迁移	                                    Yes
metrics	        展示当前应用的 metrics 信息	                                Yes
mappings	    显示所有 @RequestMapping 路径集列表	                        Yes
scheduledtasks	显示应用程序中的计划任务	                                    Yes
sessions	    允许从Spring会话支持的会话存储中检索和删除用户会话。	        Yes
shutdown	    允许应用以优雅的方式关闭（默认情况下不启用）	                Yes
threaddump	    执行一个线程dump	                                            Yes
httptrace	    显示HTTP跟踪信息（默认显示最后100个HTTP请求 – 响应交换）	    Yes

3、导入依赖
在 pom.xml 中添加 spring-boot-starter-actuator 的依赖

4、属性配置
在 application.properties 文件中配置actuator的相关配置，其中info开头的属性，就是访问info端点中显示的相关内容，值得注意的是Spring Boot2.x中，默认只开放了info、health两个端点，剩余的需要自己通过配置management.endpoints.web.exposure.include属性来加载（有include自然就有exclude，不做详细概述了）。如果想单独操作某个端点可以使用management.endpoint.端点.enabled属性进行启用或禁用

5、简单测试

启动项目，访问 http://localhost:8080/actuator/info 看到如下内容代表配置成功

6、自定义 – 重点
上面讲了很多都是配置相关，以及自带的一些端点，在实际应用中有时候默认并不能满足我们的要求，比如Spring Boot默认的健康端点就很有可能不能满足

默认装配 HealthIndicators
下列是依赖spring-boot-xxx-starter后相关HealthIndicator的实现（通过management.health.defaults.enabled 属性可以禁用它们），但想要获取一些额外的信息时，自定义的作用就体现出来了…
名称	                                描述
CassandraHealthIndicator	        检查 Cassandra 数据库是否启动。
DiskSpaceHealthIndicator	        检查磁盘空间不足。
DataSourceHealthIndicator	        检查是否可以获得连接 DataSource。
ElasticsearchHealthIndicator	    检查 Elasticsearch 集群是否启动。
InfluxDbHealthIndicator	            检查 InfluxDB 服务器是否启动。
JmsHealthIndicator	                检查 JMS 代理是否启动。
MailHealthIndicator	                检查邮件服务器是否启动。
MongoHealthIndicator	            检查 Mongo 数据库是否启动。
Neo4jHealthIndicator	            检查 Neo4j 服务器是否启动。
RabbitHealthIndicator	            检查 Rabbit 服务器是否启动。
RedisHealthIndicator	            检查 Redis 服务器是否启动。
SolrHealthIndicator	                检查 Solr 服务器是否已启动。

健康端点（第一种方式）
实现HealthIndicator接口，根据自己的需要判断返回的状态是UP还是DOWN，功能简单。
简单测试
启动项目，访问 http://localhost:8080/actuator/health 看到如下内容代表配置成功

健康端点（第二种方式）
继承AbstractHealthIndicator抽象类，重写doHealthCheck方法，功能比第一种要强大一点点，默认的DataSourceHealthIndicator 、 RedisHealthIndicator 都是这种写法，内容回调中还做了异常的处理。
简单测试
启动项目，访问 http://localhost:8080/actuator/health 看到如下内容代表配置成功

7、定义自己的端点
上面介绍的 info、health 都是spring-boot-actuator内置的，真正要实现自己的端点还得通过@Endpoint、 @ReadOperation、@WriteOperation、@DeleteOperation。

注解介绍
不同请求的操作，调用时缺少必需参数，或者使用无法转换为所需类型的参数，则不会调用操作方法，响应状态将为400（错误请求）
@Endpoint 构建 rest api 的唯一路径
@ReadOperation GET请求，响应状态为 200 如果没有返回值响应 404（资源未找到）
@WriteOperation POST请求，响应状态为 200 如果没有返回值响应 204（无响应内容）
@DeleteOperation DELETE请求，响应状态为 200 如果没有返回值响应 204（无响应内容）

MyEndPoint 

测试
完成准备事项后，启动Chapter13Application 访问 http://localhost:8080/actuator/battcn 看到如下内容代表配置成功…


**SpringBoot 2.x 第十五篇：actuator与spring-boot-admin 可以说的秘密**

1、什么是SBA
SBA 全称 Spring Boot Admin 是一个管理和监控 Spring Boot 应用程序的开源项目。分为admin-server 与 admin-client 两个组件，admin-server通过采集 actuator 端点数据，显示在 spring-boot-admin-ui 上，已知的端点几乎都有进行采集，通过 spring-boot-admin 可以动态切换日志级别、导出日志、导出heapdump、监控各项指标 等等….

Spring Boot Admin 在对单一应用服务监控的同时也提供了集群监控方案，支持通过eureka、consul、zookeeper等注册中心的方式实现多服务监控与管理…

2、导入依赖
在 pom.xml 中添加 spring-boot-admin 的相关依赖，这里只演示单机版本的，因此就自己监控自己了

3、属性配置
这个management.endpoints.web.base-path属性比较重要，因为Spring Boot2.x后每个端点默认的路径是/actuator/endpointId这样一来Spring Boot Admin是无法正常采集的

4、主函数
添加上 @EnableAdminServer 注解即代表是Server端，集成UI的

5、测试
完成准备事项后，启动Chapter14Application 访问 http://localhost:8080/login 看到登陆页面则代表一切正常，接着输入账号密码点击登陆即可…