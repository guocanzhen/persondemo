**SpringBoot 2.x 第六篇：整合SpringDataJpa**
1、[上一篇][Link 1]介绍了Spring JdbcTemplate的使用，对比原始的JDBC而言，它更加的简洁。但随着表的增加，重复的CRUD工作让我们苦不堪言，这时候Spring Data Jpa的作用就体现出来了…..
2、JPA
JPA是Java Persistence API的简称，中文名Java持久层API，是官方（Sun）在JDK5.0后提出的Java持久化规范。其目的是为了简化现有JAVA EE和JAVA SE应用开发工作，以及整合现有的ORM技术实现规范统一

3、JPA的总体思想和现有Hibernate、TopLink、JDO等ORM框架大体一致。总的来说，JPA包括以下3方面的技术：

ORM映射元数据： 支持XML和注解两种元数据的形式，元数据描述对象和表之间的映射关系，框架据此将实体对象持久化到数据库表中；
API： 操作实体对象来执行CRUD操作，框架在后台替代我们完成所有的事情，开发者从繁琐的JDBC和SQL代码中解脱出来。
查询语言： 通过面向对象而非面向数据库的查询语言查询数据，避免程序的SQL语句紧密耦合。
JPA只是一种规范，它需要第三方自行实现其功能，在众多框架中Hibernate是最为强大的一个。从功能上来说，JPA就是Hibernate功能的一个子集。Hibernate 从3.2开始，就开始兼容JPA。同时 Hibernate3.2获得了Sun TCK的JPA(Java Persistence API) 兼容认证。

4、Spring Data JPA
常见的ORM框架中Hibernate的JPA最为完整，因此Spring Data JPA 是采用基于JPA规范的Hibernate框架基础下提供了Repository层的实现。Spring Data Repository极大地简化了实现各种持久层的数据库访问而写的样板代码量，同时CrudRepository提供了丰富的CRUD功能去管理实体类。

优点

丰富的API，简单操作无需编写额外的代码
丰富的SQL日志输出
缺点

学习成本较大，需要学习HQL
配置复杂，虽然SpringBoot简化的大量的配置，关系映射多表查询配置依旧不容易
性能较差，对比JdbcTemplate、Mybatis等ORM框架，它的性能无异于是最差的

5、ddl-auto 几种属性

create： 每次运行程序时，都会重新创建表，故而数据会丢失
create-drop： 每次运行程序时会先创建表结构，然后待程序结束时清空表
upadte： 每次运行程序，没有表时会创建表，如果对象发生改变会更新表结构，原有数据不会清空，只会更新（推荐使用）
validate： 运行程序会校验数据与数据库的字段类型是否相同，字段不同会报错

6、实体类
JPA规范注解坐落在javax.persistence包下，@Id注解一定不要引用错了，否则会报错。@GeneratedValue(strategy = GenerationType.IDENTITY)自增策略，不需要映射的字段可以通过@Transient注解排除掉


常见的几种自增策略

TABLE： 使用一个特定的数据库表格来保存主键
SEQUENCE： 根据底层数据库的序列来生成主键，条件是数据库支持序列。这个值要与generator一起使用，generator 指定生成主键使用的生成器（可能是orcale中自己编写的序列）。
IDENTITY： 主键由数据库自动生成（主要是支持自动增长的数据库，如mysql）
AUTO： 主键由程序控制，也是GenerationType的默认值。

7、Repository
创建UserRepository数据访问层接口，需要继承JpaRepository<T,K>，第一个泛型参数是实体对象的名称，第二个是主键类型。只需要这样简单的配置，该UserRepository就拥常用的CRUD功能，JpaRepository本身就包含了常用功能，剩下的查询我们按照规范写接口即可，JPA支持@Query注解写HQL，也支持findAllByUsername这种根据字段名命名的方式（强烈推荐IntelliJ IDEA对JPA支持非常NICE）

8、测试
完成数据访问层接口后，最后编写一个junit测试类来检验代码的正确性。

下面的几个操作中，只有findAllByUsername是我们自己编写的代码，其它的都是继承自JpaRepository接口中的方法，更关键的是分页及排序是如此的简单实例化一个Pageable即可…
