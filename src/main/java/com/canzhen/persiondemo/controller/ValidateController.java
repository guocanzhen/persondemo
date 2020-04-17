package com.canzhen.persiondemo.controller;

import com.canzhen.persiondemo.bean.ValidateBook;
import com.canzhen.persiondemo.bean.ValidateGroupBook;
import com.canzhen.persiondemo.annotation.DateTime;
import com.canzhen.persiondemo.validate.ValidateGroups;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 参数校验
 */
//@Validated： 开启数据有效性校验，添加在类上即为验证方法，添加在方法参数中即为验证参数对象。（添加在方法上无效）
@Validated
@RestController
public class ValidateController {

    //    下面这段代码很多人一定见到过，就是对参数进行有效性校验，但仔细观察的话就会发现；
// 随着参数的增加，格式的变化，校验数据有效性的代码愈发的繁琐杂乱
    @GetMapping("/testValidate1")
    public String test1(String name) {
        if (name == null) {
            throw new NullPointerException("name 不能为空");
        }
        if (name.length() < 2 || name.length() > 10) {
            throw new RuntimeException("name 长度必须在 2 - 10 之间");
        }
        return "success";
    }

//普通参数属性验证
//    @NotBlank： 被注释的字符串不允许为空（value.trim() > 0 ? true : false）
//    @Length： 被注释的字符串的大小必须在指定的范围内
    @GetMapping("/testValidate2")
    public String test2(@NotBlank(message = "name 不能为空") @Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间") String name) {
        return "success";
    }

//对象的验证
    @GetMapping("/testValidate3")
    public String test3(@Validated ValidateBook book) {
        return "success";
    }

//自定义注解测试
    @GetMapping("/testCustomValidate")
    public String test(@DateTime(message = "您输入的格式错误，正确的格式为：{format}", format = "yyyy-MM-dd HH:mm") @NotBlank(message = "日期不能为空") String date) {
        return "success";
    }

//    分组校验groups
//    定义好 insert、update 俩个方法，比由于 insert 方法并不关心 ID 字段，
// 所以这里 @Validated 的 value 属性写成 Groups.Default.class 就可以了；
// 而 update 方法需要去验证 ID 是否为空，
// 所以此处 @Validated 注解的 value 属性值就要写成 Groups.Default.class, Groups.Update.class；
// 代表只要是这分组下的都需要进行数据有效性校验操作…
    @GetMapping("/insertValidate")
    public String insert(@Validated(value = ValidateGroups.Default.class) ValidateGroupBook book) {
        return "insert";
    }

    @GetMapping("/updateValidate")
    public String update(@Validated(value = {ValidateGroups.Default.class, ValidateGroups.Update.class}) ValidateGroupBook book) {
        return "update";
    }
}
