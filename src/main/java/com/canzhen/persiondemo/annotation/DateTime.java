package com.canzhen.persiondemo.annotation;

import com.canzhen.persiondemo.validate.DateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//自定义注解
//这里定义了一个 @DateTime 注解，在该注解上标注了 @Constraint 注解，它的作用就是指定一个具体的校验器类
//关键字段（强制性）
//message： 验证失败提示的消息内容
//groups： 为约束指定验证组（非常不错的一个功能，下一章介绍）
//payload： 不太清楚（欢迎留言交流）
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = DateTimeValidator.class)
public @interface DateTime {
    String message() default "格式错误";

    String format() default "yyyy-MM-dd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
