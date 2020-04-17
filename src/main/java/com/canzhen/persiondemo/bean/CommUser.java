package com.canzhen.persiondemo.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//通用Mapper用户密码
//通用Mapper采用了JPA规范包中的注解，这种的设计避免了重复造轮子，更是让Spring Data Jpa的应用可以轻松切换到Mybatis

@Getter
@Setter
@Table(name = "t_user")
public class CommUser implements Serializable {
    private static final long serialVersionUID = 8655851615465363473L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public CommUser() {
    }

    public CommUser(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
