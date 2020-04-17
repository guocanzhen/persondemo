package com.canzhen.persiondemo.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//redis测试用--用户密码
@Getter
@Setter
public class RedisUser implements Serializable {
    private static final long serialVersionUID = 8655851615465363473L;
    private Long id;
    private String username;
    private String password;

    public RedisUser() {
    }

    public RedisUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
