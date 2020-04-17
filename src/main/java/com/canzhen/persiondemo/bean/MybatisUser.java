package com.canzhen.persiondemo.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//mybatis用户密码
@Getter
@Setter
public class MybatisUser implements Serializable {
    private static final long serialVersionUID = 8655851615465363473L;
    private long id;
    private String username;
    private String password;

    public MybatisUser() {
    }

    public MybatisUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
