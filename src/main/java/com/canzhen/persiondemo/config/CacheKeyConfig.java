package com.canzhen.persiondemo.config;

import com.canzhen.persiondemo.impl.LockKeyGenerator;
import com.canzhen.persiondemo.inter.generator.CacheKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//这里需要注入前面定义好的 CacheKeyGenerator 接口具体实现…

@Configuration
public class CacheKeyConfig {
    @Bean
    public CacheKeyGenerator cacheKeyGenerator() {
        return new LockKeyGenerator();
    }
}
