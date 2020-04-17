package com.canzhen.persiondemo.bean;

import lombok.Getter;
import lombok.Setter;

//template用户密码
@Getter
@Setter
public class TemplateUser {
    private long id;
    private String username;
    private String password;

    public TemplateUser() {
    }

    public TemplateUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
