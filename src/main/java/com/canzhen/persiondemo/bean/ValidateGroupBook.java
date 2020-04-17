package com.canzhen.persiondemo.bean;

import com.canzhen.persiondemo.validate.ValidateGroups;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

//分组校验测试实体类
@Getter
@Setter
public class ValidateGroupBook {
    @NotNull(message = "id 不能为空", groups = ValidateGroups.Update.class)
    private Integer id;
    @NotBlank(message = "name 不允许为空", groups = ValidateGroups.Default.class)
    private String name;
    @NotNull(message = "price 不允许为空", groups = ValidateGroups.Default.class)
    private BigDecimal price;
}
