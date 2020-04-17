package com.canzhen.persiondemo.config;

import com.canzhen.persiondemo.endpoint.MyEndPoint;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//MyEndPoint的bean的声明
@Configuration
public class MyEndpointConfig {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public MyEndPoint myEndPoint() {
        return new MyEndPoint();
    }
}
