 **SpringBoot 2.x 第七篇：整合Mybatis**
 
 1、MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射，几乎避免了所有的 JDBC 代码和手动设置参数以及获取结果集，使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java 对象)映射成数据库中的记录，在国内可谓是占据了半壁江山……
 
 2、ORM对比图
 以下针对**Spring JDBC、Spring Data Jpa、Mybatis**三款框架做了个粗略的对比。一般应用的性能瓶颈并不是在于ORM，所以这三个框架技术选型应该考虑项目的场景、团队的技能掌握情况、开发周期(开发效率)…
 
 框架对比	Spring JDBC 	Spring Data Jpa	    Mybatis
 性能	    性能最好	        性能最差	            居中
 代码量	    多	            少	                多
 学习成本	低	            高	                居中
 推荐指数	❤❤❤	        ❤❤❤❤❤   	        ❤❤❤❤❤
 
 对于业务复杂且对性能要求较高的项目来说**Mybatis**往往能更好的胜任，可以自己进行SQL优化，同时更让我喜欢的是Mybatis分页插件与通用Mapper(单表CURD无需自己手写)有了这两款插件的支持，还有什么理由拒绝Mybatis呢
 
 
 3、持久层
 这里提供了两种方式操作接口，第一种带@Select注解的是Mybatis3.x提供的新特性，同理它还有@Update、@Delete、@Insert等等一系列注解，第二种就是传统方式了，写个接口映射，然后在XML中写上我们的SQL语句…
 
 
 