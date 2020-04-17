package com.canzhen.persiondemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author canzhen
 * @since 2020/03/19
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "my1")
public class My1Properties {
    private int age;
    private String name;
}
