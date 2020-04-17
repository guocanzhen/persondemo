package com.canzhen.persiondemo.exception;

import lombok.Getter;
import lombok.Setter;

//异常信息模板
//定义返回的异常信息的格式，这样异常信息风格更为统一
@Getter
@Setter
public class ErrorResponseBean {
    private int code;
    private String message;

    public ErrorResponseBean() {
    }

    public ErrorResponseBean(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
