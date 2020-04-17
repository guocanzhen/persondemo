**SpringBoot 2.x | 第十二篇：初探RabbitMQ消息队列**

MQ全称（Message Queue）又名消息队列，是一种异步通讯的中间件。可以将它理解成邮局，发送者将消息传递到邮局，然后由邮局帮我们发送给具体的消息接收者（消费者），具体发送过程与时间我们无需关心，它也不会干扰我进行其它事情。常见的MQ有kafka、activemq、zeromq、rabbitmq 等等，各大MQ的对比和优劣势可以自行Google

安装：https://blog.csdn.net/zhm3023/article/details/82217222

添加用户及设置权限：https://blog.csdn.net/qq_35781732/article/details/79807160

1、rabbitmq
RabbitMQ是一个遵循AMQP协议，由面向高并发的erlanng语言开发而成，用在实时的对可靠性要求比较高的消息传递上，支持多种语言客户端。支持延迟队列（这是一个非常有用的功能）….

2、基础概念
Broker：简单来说就是消息队列服务器实体
Exchange：消息交换机，它指定消息按什么规则，路由到哪个队列
Queue：消息队列载体，每个消息都会被投入到一个或多个队列
Binding：绑定，它的作用就是把exchange和queue按照路由规则绑定起来
Routing Key：路由关键字，exchange根据这个关键字进行消息投递
vhost：虚拟主机，一个broker里可以开设多个vhost，用作不同用户的权限分离
producer：消息生产者，就是投递消息的程序
consumer：消息消费者，就是接受消息的程序
channel：消息通道，在客户端的每个连接里，可建立多个channel，每个channel代表一个会话任务

基于Centos7.x安装请参考： http://blog.battcn.com/2017/08/20/linux/linux-centos7-ribbitmq/


3、常见应用场景
邮箱发送：用户注册后投递消息到rabbitmq中，由消息的消费方异步的发送邮件，提升系统响应速度
流量削峰：一般在秒杀活动中应用广泛，秒杀会因为流量过大，导致应用挂掉，为了解决这个问题，一般在应用前端加入消息队列。用于控制活动人数，将超过此一定阀值的订单直接丢弃。缓解短时间的高流量压垮应用。
订单超时：利用rabbitmq的延迟队列，可以很简单的实现订单超时的功能，比如用户在下单后30分钟未支付取消订单
还有更多应用场景就不一一列举了…..

4、属性配置
在 application.properties 文件中配置rabbitmq相关内容，值得注意的是这里配置了手动ACK的开关

5、具体编码
定义队列
如果手动创建过或者RabbitMQ中已经存在该队列那么也可以省略下述代码…

实体类
创建一个Book类

控制器
编写一个Controller类，用于消息发送工作

消息消费者
默认情况下 spring-boot-data-amqp 是自动ACK机制，就意味着 MQ 会在消息消费完毕后自动帮我们去ACK，这样依赖就存在这样一个问题：如果报错了，消息不会丢失，会无限循环消费，很容易就吧磁盘空间耗完，虽然可以配置消费的次数但这种做法也有失优雅。目前比较推荐的就是我们手动ACK然后将消费错误的消息转移到其它的消息队列中，做补偿处理

6、RabbitMQ Managerment可视化: http://192.168.31.86:15672/

**SpringBoot 2.x 第十三篇：RabbitMQ延迟队列**

初探RabbitMQ消息队列中介绍了RabbitMQ的简单用法，顺带提及了下延迟队列的作用。所谓延时消息就是指当消息被发送以后，并不想让消费者立即拿到消息，而是等待指定时间后，消费者才拿到这个消息进行消费。

1、延迟队列
延迟队列能做什么？

订单业务： 在电商/点餐中，都有下单后 30 分钟内没有付款，就自动取消订单。
短信通知： 下单成功后 60s 之后给用户发送短信通知。
失败重试： 业务操作失败后，间隔一定的时间进行失败重试。
这类业务的特点就是：非实时的，需要延迟处理，需要进行失败重试。一种比较笨的方式是采用定时任务，轮训数据库，方法简单好用，但性能底下，在高并发情况下容易弄死数据库，间隔时间不好设置，时间过大，影响精度，过小影响性能，而且做不到按超时的时间顺序处理。另一种就是用Java中的DelayQueue 位于java.util.concurrent包下，本质是由PriorityQueue和BlockingQueue实现的阻塞优先级队列。，这玩意最大的问题就是不支持分布式与持久化

2、RabbitMQ 实现思路
RabbitMQ队列本身是没有直接实现支持延迟队列的功能，但可以通过它的Time-To-Live Extensions 与 Dead Letter Exchange 的特性模拟出延迟队列的功能。

Time-To-Live Extensions
RabbitMQ支持为队列或者消息设置TTL（time to live 存活时间）。TTL表明了一条消息可在队列中存活的最大时间。当某条消息被设置了TTL或者当某条消息进入了设置了TTL的队列时，这条消息会在TTL时间后死亡成为Dead Letter。如果既配置了消息的TTL，又配置了队列的TTL，那么较小的那个值会被取用。

Dead Letter Exchange
死信交换机，上文中提到设置了 TTL 的消息或队列最终会成为Dead Letter。如果为队列设置了Dead Letter Exchange（DLX），那么这些Dead Letter就会被重新发送到Dead Letter Exchange中，然后通过Dead Letter Exchange路由到其他队列，即可实现延迟队列的功能。

具体流程可查看：readme/DeadLetterExchange.jpg


3、具体编码
定义队列
如果手动创建过或者RabbitMQ中已经存在该队列那么也可以省略下述代码…

实体类
创建一个Book类

控制器
编写一个Controller类，用于消息发送工作，同时为了看到测试效果，添加日志输出，将发送消息的时间记录下来..

消息消费者
默认情况下 spring-boot-data-amqp 是自动ACK机制，就意味着 MQ 会在消息消费完毕后自动帮我们去 ACK，这样依赖就存在这样一个问题：如果报错了，消息不会丢失，会无限循环消费，很容易就吧磁盘空间耗完，虽然可以配置消费的次数但这种做法也有失优雅。目前比较推荐的就是我们手动ACK然后将消费错误的消息转移到其它的消息队列中，做补偿处理。 由于我们需要手动控制ACK，因此下面监听完消息后需要调用basicAck通知rabbitmq消息已被正确消费，可以将远程队列中的消息删除

4、RabbitMQ Managerment可视化: http://192.168.31.86:15672/