**SpringBoot 2.x 第二十二篇：轻松搞定重复提交（本地锁）**

在平时开发中，如果网速比较慢的情况下，用户提交表单后，发现服务器半天都没有响应，那么用户可能会以为是自己没有提交表单，就会再点击提交按钮重复提交表单，我们在开发中必须防止表单重复提交….

单机版：
1、重复提交
字面意思就是提交了很多次，这种情况一般都是前端给你挖的坑….
前段时间在开发中遇到一个这样的问题；前端小哥哥调用接口的时候存在 循环调用 的问题，正常情况下发送一个请求添加一条数据，结果变成了同一时刻并发的发送了 N 个请求，服务端瞬间懵逼的插入了 N 条一模一样的数据，前端小哥哥也不知道问题在哪里（恩...坑就这样挖好了，反正不填坑，气死你） 这时候咋办呢；后端干呗，反正脏活累活，背锅的事情也没少干了，多一件也不多….

2、本章目标
利用 自定义注解、Spring Aop、Guava Cache 实现表单防重复提交（不适用于分布式哦，后面会讲分布式方式...）

3、具体代码

Lock 注解
创建一个 LocalLock 注解，简单点就一个 key 可以了，由于暂时未用到 redis 所以 expire 是摆设….

Lock 拦截器（AOP）
首先通过 CacheBuilder.newBuilder() 构建出缓存对象，设置好过期时间；其目的就是为了防止因程序崩溃锁得不到释放（当然如果单机这种方式程序都炸了，锁早没了；但这不妨碍我们写好点）
在具体的 interceptor() 方法上采用的是 Around（环绕增强） ，所有带 LocalLock 注解的都将被切面处理；
如果想更为灵活，key 的生成规则可以定义成接口形式（可以参考：org.springframework.cache.interceptor.KeyGenerator），这里就偷个懒了；

控制层
在接口上添加 @LocalLock(key = "book:arg[0]")；意味着会将 arg[0] 替换成第一个参数的值，生成后的新 key 将被缓存起来；


**SpringBoot 2.x 第二十三篇：轻松搞定重复提交（分布式锁）**

在 一起来学SpringBoot | 第二十二篇：轻松搞定重复提交（一） 一文中介绍了单机版的重复提交解决方案，在如今这个分布式与集群横行的世道中，那怎么够用呢，所以本章重点来了....

分布式：
1、重复提交（分布式）
单机版中我们用的是Guava Cache，但是这玩意存在集群的时候就凉了，所以我们还是要借助类似Redis、ZooKeeper 之类的中间件实现分布式锁。

2、本章目标
利用 自定义注解、Spring Aop、Redis Cache 实现分布式锁，你想锁表单锁表单，想锁接口锁接口….

3、具体代码

导入依赖
在 pom.xml 中添加上 starter-web、starter-aop、starter-data-redis 的依赖即可

CacheLock 注解
创建一个 CacheLock 注解，本章内容都是实战使用过的，所以属性配置会相对完善了，话不多说注释都给各位写齐全了….
prefix： 缓存中 key 的前缀
expire： 过期时间，此处默认为 5 秒
timeUnit： 超时单位，此处默认为秒
delimiter： key 的分隔符，将不同参数值分割开来

CacheParam 注解
上一篇中给说过 key 的生成规则是自己定义的，如果通过表达式语法自己得去写解析规则还是比较麻烦的，所以依旧是用注解的方式…

Key 生成策略（接口）
创建一个 CacheKeyGenerator 具体实现由使用者自己去注入

Key 生成策略（实现）
解析过程虽然看上去优点绕，但认真阅读或者调试就会发现，主要是解析带 CacheLock 注解的属性，获取对应的属性值，生成一个全新的缓存 Key

Lock 拦截器（AOP）
熟悉 Redis 的朋友都知道它是线程安全的，我们利用它的特性可以很轻松的实现一个分布式锁，如 opsForValue().setIfAbsent(key,value) 它的作用就是如果缓存中没有当前 Key 则进行缓存同时返回 true 反之亦然；当缓存后给 key 在设置个过期时间，防止因为系统崩溃而导致锁迟迟不释放形成死锁； 那么我们是不是可以这样认为当返回 true 我们认为它获取到锁了，在锁未释放的时候我们进行异常的抛出….

RedisLockHelper
通过封装成 API 方式调用，灵活度更加高

控制层
在接口上添加 @CacheLock(prefix = "books")，然后动态的值可以加上@CacheParam；生成后的新 key 将被缓存起来；（如：该接口 token = 1，那么最终的 key 值为 books:1，如果多个条件则依次类推）
