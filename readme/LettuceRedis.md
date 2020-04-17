**SpringBoot 2.x 第九篇：整合Lettuce Redis**

redis应用场景：https://www.cnblogs.com/shiqi17/p/9581752.html

安装链接：https://www.ycbbs.vip/?p=1428

1、Spring Boot 除了支持常见的 ORM 框架外，更是对常用的中间件提供了非常好封装，随着Spring Boot2.x的到来，支持的组件越来越丰富，也越来越成熟，其中对Redis的支持不仅仅是丰富了它的 API，更是替换掉底层Jedis的依赖，取而代之换成了Lettuce(生菜)

2、Redis介绍
Redis 是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。相比Memcached它支持存储的类型相对更多（字符、哈希、集合、有序集合、列表、GEO），同时Redis是线程安全的。2010年3月15日起，Redis的开发工作由VMware主持，2013年5月开始，Redis的开发由Pivotal赞助。

Redis最广泛的应用场景就是Cache

3、Lettuce
Lettuce 和 Jedis 的都是连接Redis Server的客户端程序。Jedis在实现上是直连redis server，多线程环境下非线程安全，除非使用连接池，为每个Jedis实例增加物理连接。Lettuce基于Netty的连接实例（StatefulRedisConnection），可以在多个线程间并发访问，且线程安全，满足多线程环境下的并发访问，同时它是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。

4、Spring Boot2.x 后底层不在是Jedis如果做版本升级的朋友需要注意下

5、spring-boot-starter-data-redis的依赖
添加redis依赖和连接池commons-pool2依赖

6、属性配置
在 application.properties 文件中配置如下内容，由于Spring Boot2.x 的改动，连接池相关配置需要通过spring.redis.lettuce.pool 或者 spring.redis.jedis.pool 进行配置了

7、自定义Template
默认情况下的模板只能支持RedisTemplate<String, String>，也就是只能存入字符串，这在开发中是不友好的，所以自定义模板是很有必要的，当自定义了模板又想使用String存储这时候就可以使用StringRedisTemplate的方式，它们并不冲突…

8、其它类型

下列的就是Redis其它类型所对应的操作方式

opsForValue： 对应 String（字符串）
opsForZSet： 对应 ZSet（有序集合）
opsForHash： 对应 Hash（哈希）
opsForList： 对应 List（列表）
opsForSet： 对应 Set（集合）
opsForGeo： 对应 GEO（地理位置）

9、总结
spring-data-redis文档： https://docs.spring.io/spring-data/redis/docs/2.0.1.RELEASE/reference/html/#new-in-2.0.0
Redis 文档： https://redis.io/documentation
Redis 中文文档： http://www.redis.cn/commands.html


**SpringBoot 2.x 第十篇：使用Spring Cache集成Redis**

Spring 3.1 引入了激动人心的基于注释（annotation）的缓存（cache）技术，它本质上不是一个具体的缓存实现方案（例如 EHCache 或者 Redis），而是一个对缓存使用的抽象，通过在既有代码中添加少量它定义的各种 annotation，即能够达到缓存方法的返回对象的效果。

1、特点
具备相当的好的灵活性，不仅能够使用 SpEL（Spring Expression Language）来定义缓存的 key 和各种 condition，还提供开箱即用的缓存临时存储方案，也支持和主流的专业缓存例如 EHCache、Redis、Guava 的集成。

基于 annotation 即可使得现有代码支持缓存
开箱即用 Out-Of-The-Box，不用安装和部署额外第三方组件即可使用缓存
支持 Spring Express Language，能使用对象的任何属性或者方法来定义缓存的 key 和 condition
支持 AspectJ，并通过其实现任何方法的缓存支持
支持自定义 key 和自定义缓存管理者，具有相当的灵活性和扩展性


2、属性配置
使用了Spring Cache后，能指定spring.cache.type就手动指定一下，虽然它会自动去适配已有Cache的依赖，但先后顺序会对Redis使用有影响（JCache -> EhCache -> Redis -> Guava）

3、根据条件操作缓存
根据条件操作缓存内容并不影响数据库操作，条件表达式返回一个布尔值，true/false，当条件为true，则进行缓存操作，否则直接调用方法执行的返回结果。

长度： @CachePut(value = "user", key = "#user.id",condition = "#user.username.length() < 10") 只缓存用户名长度少于10的数据
大小： @Cacheable(value = "user", key = "#id",condition = "#id < 10") 只缓存ID小于10的数据
组合： @Cacheable(value="user",key="#user.username.concat(##user.password)")
提前操作： @CacheEvict(value="user",allEntries=true,beforeInvocation=true) 加上beforeInvocation=true后，不管内部是否报错，缓存都将被清除，默认情况为false


4、注解介绍
@Cacheable(根据方法的请求参数对其结果进行缓存)
key： 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合（如：@Cacheable(value="user",key="#userName")）
value： 缓存的名称，必须指定至少一个（如：@Cacheable(value="user") 或者@Cacheable(value={"user1","use2"})）
condition： 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存（如：@Cacheable(value = "user", key = "#id",condition = "#id < 10")）

@CachePut(根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用)
key： 同上
value： 同上
condition： 同上

@CachEvict(根据条件对缓存进行清空)
key： 同上
value： 同上
condition： 同上
allEntries： 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存（如：@CacheEvict(value = "user", key = "#id", allEntries = true)）
beforeInvocation： 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存（如：@CacheEvict(value = "user", key = "#id", beforeInvocation = true)）

5、总结
spring-cache文档： https://docs.spring.io/spring/docs/5.0.5.RELEASE/spring-framework-reference/integration.html#cache-introduction
spring-data-redis文档： https://docs.spring.io/spring-data/redis/docs/2.0.1.RELEASE/reference/html/#new-in-2.0.0
Redis 文档： https://redis.io/documentation
Redis 中文文档： http://www.redis.cn/commands.html