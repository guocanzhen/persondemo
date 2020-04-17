package com.canzhen.persiondemo.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//swagger 提供了非常齐全的注解，为POJO提供了@ApiModel、@ApiModelProperty，以便更好的渲染最终结果
@ApiModel
@Getter
@Setter
public class SwaggerUser implements Serializable {
    private static final long serialVersionUID = 8655851615465363473L;

    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;

    public SwaggerUser() {
    }

    public SwaggerUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
