package com.canzhen.persiondemo.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

//Jpa用户密码
@Entity(name = "t_user")
@Getter
@Setter
public class JpaUser implements Serializable {
    private static final long serialVersionUID = 8655851615465363473L;
//    JPA规范注解坐落在javax.persistence包下，@Id注解一定不要引用错了，否则会报错
//    @GeneratedValue(strategy = GenerationType.IDENTITY)自增策略
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    /**
     * TODO 忽略该字段的映射
     * 不需要映射的字段可以通过@Transient注解排除掉
     */
    @Transient
    private String  email;


    public JpaUser() {
    }

    public JpaUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public JpaUser(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
