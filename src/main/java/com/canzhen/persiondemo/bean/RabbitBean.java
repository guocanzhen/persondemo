package com.canzhen.persiondemo.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//RabbitMQ测试实体类
@Getter
@Setter
public class RabbitBean implements Serializable {
    private static final long serialVersionUID = -2164058270260403154L;

    private String id;
    private String name;
}
