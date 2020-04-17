package com.canzhen.persiondemo.impl;

import com.canzhen.persiondemo.bean.RedisUser;
import com.canzhen.persiondemo.inter.service.RedisUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class RedisUserServiceImpl implements RedisUserService {
//    为了方便演示数据库操作，直接定义了一个Map<Long, User> DATABASES，这里的核心就是@Cacheable、@CachePut、@CacheEvict 三个注解
//    private static final Map<Long, RedisUser> DATABASES = new HashMap<>();
//
//    static {
//        DATABASES.put(1L, new RedisUser(1L, "u1", "p1"));
//        DATABASES.put(2L, new RedisUser(2L, "u2", "p2"));
//        DATABASES.put(3L, new RedisUser(3L, "u3", "p3"));
//    }

    private static final Logger log = LoggerFactory.getLogger(RedisUserServiceImpl.class);

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

//    @CachePut(根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用)
    @CachePut(value = "user", key = "#user.id")
    @Override
    public RedisUser saveOrUpdate(RedisUser user) {
//        DATABASES.put(user.getId(), user);
        log.info("进入 saveOrUpdate 方法");

        redisCacheTemplate.opsForValue().set(user.getId().toString(),user);
        RedisUser redisUser = (RedisUser) redisCacheTemplate.opsForValue().get(user.getId().toString());
        log.info("[Redis 添加] - [{}]",redisUser);

        return user;
    }

//    @Cacheable(根据方法的请求参数对其结果进行缓存)
//    @Cacheable(value = "user", key = "#id")
    @CacheEvict(value = "user",key = "#id",allEntries = true)
    @Override
    public RedisUser get(Long id) {
        // TODO 我们就假设它是从数据库读取出来的
        log.info("进入 get 方法");
//        return DATABASES.get(id);
        return (RedisUser) redisCacheTemplate.opsForValue().get(id.toString());
    }

//    @CachEvict(根据条件对缓存进行清空)
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Long id) {
//        DATABASES.remove(id);
        redisCacheTemplate.delete(id.toString());

        log.info("进入 delete 方法");
    }
}
