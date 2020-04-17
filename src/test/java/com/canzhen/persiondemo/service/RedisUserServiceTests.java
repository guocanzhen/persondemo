//package com.canzhen.persiondemo.service;
//
//import com.canzhen.persiondemo.bean.RedisUser;
//import com.canzhen.persiondemo.inter.service.RedisUserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RedisUserServiceTests {
//    private static final Logger log = LoggerFactory.getLogger(RedisUserServiceTests.class);
//
//    @Autowired
//    private RedisUserService redisUserService;
//
//    @Test
//    public void get() {
//        final RedisUser user = redisUserService.saveOrUpdate(new RedisUser(5L, "u5", "p5"));
//        log.info("[saveOrUpdate] - [{}]", user);
//
////        启动测试类，结果和我们期望的一致，可以看到增删改查中，查询是没有日志输出的，因为它直接从缓存中获取的数据，
//// 而添加、修改、删除都是会进入方法内执行具体的业务代码，然后通过切面去删除掉Redis中的缓存数据。
//// 其中 # 号代表这是一个 SpEL 表达式，此表达式可以遍历方法的参数对象，具体语法可以参考 Spring 的相关文档手册。
//        final RedisUser redisUser = redisUserService.get(5L);
//        log.info("[get] - [{}]", redisUser);
//
////        使用@CachEvict(根据条件对缓存进行清空)allEntries： 清空所有缓存内容
//        final RedisUser user1 = redisUserService.get(1L);
//        log.info("[get] - [{}]", user1);
//
//        redisUserService.delete(5L);
//    }
//}
