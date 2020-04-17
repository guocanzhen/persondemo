package com.canzhen.persiondemo.bean;

//测试验证实体类

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ValidateBook {
    private Integer id;

//    @NotBlank： 被注释的字符串不允许为空（value.trim() > 0 ? true : false）
//    @Length： 被注释的字符串的大小必须在指定的范围内
//    @NotNull： 被注释的字段不允许为空(value != null ? true : false)
//    @DecimalMin： 被注释的字段必须大于或等于指定的数值
    @NotBlank(message = "name 不允许为空")
    @Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间")
    private String name;

    @NotNull(message = "price 不允许为空")
    @DecimalMin(value = "0.1", message = "价格不能低于 {value}")
    private BigDecimal price;

    public ValidateBook(@NotBlank(message = "name 不允许为空")
                        @Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间") String name,
                        @NotNull(message = "price 不允许为空")
                        @DecimalMin(value = "0.1", message = "价格不能低于 {value}") BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
