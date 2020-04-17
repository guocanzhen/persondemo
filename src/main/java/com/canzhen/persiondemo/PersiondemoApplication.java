package com.canzhen.persiondemo;

import com.battcn.swagger.annotation.EnableSwagger2Doc;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.Arrays;


@RestController
@SpringBootApplication
//@EnableCaching 必须要加，否则spring-data-cache相关注解不会生效…
@EnableCaching

@EnableSwagger2Doc
//添加上 @EnableAdminServer 注解即代表是Server端，集成UI的
@EnableAdminServer

//定时任务
//@EnableScheduling 注解表示开启对@Scheduled注解的解析；
// 同时new ThreadPoolTaskScheduler()也是相当的关键，通过阅读过源码可以发现默认情况下的 private volatile int poolSize = 1;
// 这就导致了多个任务的情况下容易出现竞争情况（多个任务的情况下，如果第一个任务没执行完毕，后续的任务将会进入等待状态）。
//@EnableAsync 注解表示开启@Async注解的解析；
// 作用就是将串行化的任务给并行化了。
// （@Scheduled(cron = "0/1 * * * * *")假设第一次工作时间为2018-05-29 17:30:55，工作周期为3秒；
// 如果不加@Async那么下一次工作时间就是2018-05-29 17:30:59；
// 如果加了@Async 下一次工作时间就是2018-05-29 17:30:56）
//@EnableAsync
//@EnableScheduling
public class PersiondemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersiondemoApplication.class, args);
	}

//	@GetMapping("/demo1")
//	public String demo1() {
//		return "Hello,demo1.";
//	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		// 目的是
//		return args -> {
//			System.out.println("来看看 SpringBoot 默认为我们提供的 Bean：");
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			Arrays.stream(beanNames).forEach(System.out::println);
//			System.out.println("以上是默认的Bean.......");
//		};
		return args -> {System.out.println("加载完毕");};
	}

	/**
	 * 很关键：默认情况下 TaskScheduler 的 poolSize = 1
	 *
	 * @return 线程池
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(10);
		return taskScheduler;
	}
}
