**SpringBoot 2.x | 第十六篇：定时任务详解**

在我们日常开发中，经常会遇到 数据定时增量同步、定时发送邮件、爬虫定时抓取 的需求；这时我们可以采用定时任务的方式去进行工作…..

1、定时任务概述
定时任务：顾名思义就是在指定/特定的时间进行工作，比如我们的手机闹钟，它就是一种定时任务。

2、实现方式
Timer： JDK自带的java.util.Timer；通过调度java.util.TimerTask的方式 让程序按照某一个频度执行，但不能在指定时间运行。一般用的较少。

ScheduledExecutorService： JDK1.5新增的，位于java.util.concurrent包中；是基于线程池设计的定时任务类，每个调度任务都会被分配到线程池中，并发执行，互不影响。

Spring Task： Spring3.0 以后新增了task，一个轻量级的Quartz，功能够用，用法简单。

Quartz： 功能最为强大的调度器，可以让程序在指定时间执行，也可以按照某一个频度执行，它还可以动态开关，但是配置起来比较复杂。现如今开源社区中已经很多基于Quartz 实现的分布式定时任务项目（xxl-job、elastic-job）。

3、Timer 方式
基于 Timer 实现的定时调度，基本就是手撸代码，目前应用较少，不是很推荐

4、基于 ScheduledExecutorService
与Timer很类似，但它的效果更好，多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中有一个因任务报错没有捕获抛出的异常，其它任务便会自动终止运行，使用 ScheduledExecutorService 则可以规避这个问题

5、Spring Task(本章关键)
导入依赖
在 pom.xml 中添加 spring-boot-starter-web 依赖即可，它包含了spring-context，定时任务相关的就属于这个JAR下的org.springframework.scheduling包中

定时任务
@Scheduled 定时任务的核心

cron： cron表达式，根据表达式循环执行，与fixedRate属性不同的是它是将时间进行了切割。（@Scheduled(cron = "0/5 * * * * *")任务将在5、10、15、20...这种情况下进行工作）
fixedRate： 每隔多久执行一次，无视工作时间（@Scheduled(fixedRate = 1000) 假设第一次工作时间为2018-05-29 16:58:28，工作时长为3秒，那么下次任务的时候就是2018-05-29 16:58:31）
fixedDelay： 当前任务执行完毕后等待多久继续下次任务（@Scheduled(fixedDelay = 3000) 假设第一次任务工作时间为2018-05-29 16:54:33，工作时长为5秒，那么下次任务的时间就是2018-05-29 16:54:41）
initialDelay： 第一次执行延迟时间，只是做延迟的设定，与fixedDelay关系密切，配合使用，相辅相成。
@Async 代表该任务可以进行异步工作，由原本的串行改为并行

主函数
@EnableScheduling 注解表示开启对@Scheduled注解的解析；同时new ThreadPoolTaskScheduler()也是相当的关键，通过阅读过源码可以发现默认情况下的 private volatile int poolSize = 1;这就导致了多个任务的情况下容易出现竞争情况（多个任务的情况下，如果第一个任务没执行完毕，后续的任务将会进入等待状态）。
@EnableAsync 注解表示开启@Async注解的解析；作用就是将串行化的任务给并行化了。（@Scheduled(cron = "0/1 * * * * *")假设第一次工作时间为2018-05-29 17:30:55，工作周期为3秒；如果不加@Async那么下一次工作时间就是2018-05-29 17:30:59；如果加了@Async 下一次工作时间就是2018-05-29 17:30:56）