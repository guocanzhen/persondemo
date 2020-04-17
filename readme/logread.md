相关链接：https://www.ycbbs.vip/?p=1243

**SpringBoot 2.x 第三篇：SpringBoot日志配置**
1、Spring Boot 内部采用的是 Commons Logging进行日志记录，但在底层为 Java Util Logging、Log4J2、Logback 等日志框架提供了默认配置 。
2、使用 SpringBoot 默认的 Logback 
例子：
2014-03-05 10:57:51.112  INFO 45469 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/7.0.52

Logback 是没有 FATAL级别的日志，它将被映射到 ERROR

时间日期：精确到毫秒,可以用于排序
日志级别：ERROR、WARN、INFO、DEBUG、TRACE
进程ID
分隔符：采用 --- 来标识日志开始部分
线程名：方括号括起来（可能会截断控制台输出）
Logger名：通常使用源代码的类名
日志内容：我们输出的消息

3、命令模式配置： java -jar app.jar --debug=true ， 这种命令会被 SpringBoot 解析，且优先级最高
4、资源文件配置： application.properties 配置 debug=true 即可。该配置只对 嵌入式容器、Spring、Hibernate生效，我们自己的项目想要输出 DEBUG 需要额外配置（配置规则：logging.level.<logger-name>=<level>）

5、日志输出格式配置
logging.pattern.console： 定义输出到控制台的格式（不支持JDK Logger）
logging.pattern.file： 定义输出到文件的格式（不支持JDK Logger）

6、颜色编码
如果终端支持 ANSI，默认情况下会给日志上个色，提高可读性，可以在配置文件中设置 spring.output.ansi.enabled 来改变默认值

ALWAYS： 启用 ANSI 颜色的输出。
DETECT： 尝试检测 ANSI 着色功能是否可用。
NEVER： 禁用 ANSI 颜色的输出。

7、文件保存
默认情况下，SpringBoot 仅将日志输出到控制台，不会写入到日志文件中去。如果除了控制台输出之外还想写日志文件，则需要在application.properties 设置logging.file 或 logging.path 属性。
logging.file： 将日志写入到指定的 文件 中，默认为相对路径，可以设置成绝对路径
logging.path： 将名为 spring.log 写入到指定的 文件夹 中，如（/var/log）
日志文件在达到 10MB 时进行切割，产生一个新的日志文件（如：spring.1.log、spring.2.log），新的日志依旧输出到 spring.log中去，默认情况下会记录 ERROR、WARN、INFO 级别消息。
logging.file.max-size： 限制日志文件大小
logging.file.max-history： 限制日志保留天数

8、自定义日志配置
由于日志在 ApplicationContext 之前就初始化好了，所以 SpringBoot 为我们提供了 logging.config 属性，方便我们配置自定义日志文件。默认情况它会根据日志的依赖自动加载。

**Logging System**	                **Customization**
JDK (Java Util Logging)	        logging.properties
Log4j2、ERROR	                log4j2-spring.xml 或 log4j2.xml
Logback	                        logback-spring.xml、logback-spring.groovy、logback.xml、logback.groovy

9、Logback扩展配置
该扩展配置仅适用 logback-spring.xml 或者设置 logging.config 属性的文件，因为 logback.xml 加载过早，因此无法获取 SpringBoot 的一些扩展属性

使用扩展属性 springProfile 与 springProperty 让你的 logback-spring.xml 配置显得更有逼格，当别人还在苦苦挣扎弄logback-{profile}.xml的时候 你一个文件就搞定了…

**springProfile**
<springProfile> 标签使我们让配置文件更加灵活，它可以选择性的包含或排除部分配置。

<springProfile name="dev">
    <!-- 开发环境时激活 -->
</springProfile>

<springProfile name="dev,test">
    <!-- 开发，测试的时候激活-->
</springProfile>

<springProfile name="!prod">
    <!-- 当 "生产" 环境时，该配置不激活-->
</springProfile>

**案例**
<!-- 开发环境日志级别为DEBUG/并且开发环境不写日志文件 -->
<springProfile name="dev">
    <root level="DEBUG">
        <appender-ref ref="STDOUT"/>
    </root>
</springProfile>

<!-- 测试环境日志级别为INFO/并且记录日志文件 -->
<springProfile name="test">
    <root level="INFO">
        <appender-ref ref="FILE"/>
        <appender-ref ref="STDOUT"/>
    </root>
</springProfile>
springProperty
<springProperty> 标签可以让我们在 Logback 中使用 Spring Environment 中的属性。如果想在logback-spring.xml中回读 application.properties 配置的值时，这是一个非常好的解决方案

<!-- 读取 spring.application.name 属性来生成日志文件名
    scope：作用域
    name：在 logback-spring.xml 使用的键
    source：application.properties 文件中的键
    defaultValue：默认值
 -->
<springProperty scope="context" name="logName" source="spring.application.name" defaultValue="myapp.log"/>

<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>logs/${logName}.log</file>
</appender>