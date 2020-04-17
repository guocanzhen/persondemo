**SpringBoot 2.x | 第十一篇：集成Swagger在线调试**

随着互联网技术的发展，现在的网站架构基本都由原来的后端渲染，变成了：前端渲染、前后端分离的形态，而且前端技术和后端技术在各自的道路上越走越远。

前端和后端唯一联系，变成了 API 接口；API 文档自然就成了前后端开发人员联系的纽带，变得尤为的重要，swagger就是一款让你更好的书写API文档的框架。


1、swagger 优缺点

集成方便，功能强大
在线调试与文档生成
代码耦合，需要注解支持，但不影响程序性能

2、导入依赖
在 pom.xml 中添加 swagger-spring-boot-starter 的依赖

3、属性配置
配置spring.swagger.enabled开启swagger的使用，如果在生产环境中不想用可以在对应的profile下面将它设置为spring.swagger.enabled=false，这样一来接口就不存在暴露的风险

4、实体类
swagger 提供了非常齐全的注解，为POJO提供了@ApiModel、@ApiModelProperty，以便更好的渲染最终结果

5、restful 风格接口
注解描述

@Api： 描述Controller
@ApiIgnore： 忽略该Controller，指不对当前类做扫描
@ApiOperation： 描述Controller类中的method接口
@ApiParam： 单个参数描述，与@ApiImplicitParam不同的是，他是写在参数左侧的。如（@ApiParam(name = "username",value = "用户名") String username）
@ApiModel： 描述POJO对象
@ApiProperty： 描述POJO对象中的属性值
@ApiImplicitParam： 描述单个入参信息
@ApiImplicitParams： 描述多个入参信息
@ApiResponse： 描述单个出参信息
@ApiResponses： 描述多个出参信息
@ApiError： 接口错误所返回的信息

注意

如果访问没有正常显示，请在Application.java 中添加 @EnableSwagger2Doc

6、测试
由于上面的接口是 restful 风格的接口，添加和修改无法通过浏览器完成，以前都是自己编写junit或者使用postman之类的工具。现在只需要打开浏览器输入 http://localhost:8080/swagger-ui.html，更多操作请自行体验…

