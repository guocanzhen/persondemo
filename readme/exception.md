**SpringBoot 2.x 第十八篇：轻松搞定全局异常**

实际项目开发中，程序往往会发生各式各样的异常情况，特别是身为服务端开发人员的我们，总是不停的编写接口提供给前端调用，分工协作的情况下，避免不了异常的发生，如果直接将错误的信息直接暴露给用户，这样的体验可想而知，且对黑客而言，详细异常信息往往会提供非常大的帮助…

1、初窥异常
一个简单的异常请求的接口

2、笨方法（极其不建议）
采用try-catch的方式，手动捕获异常信息，然后返回对应的结果集，相信很多人都看到过类似的代码（如：封装成 Result 对象）；该方法虽然间接性的解决错误暴露的问题，同样的弊端也很明显，增加了大量的代码量，当异常过多的情况下对应的catch层愈发的多了起来，很难管理这些业务异常和错误码之间的匹配，所以最好的方法就是通过简单配置全局掌控….

3、具体代码
通过上面的阅读大家也大致能了解到为啥需要对异常进行全局捕获了，接下来就看看 Spring Boot 提供的解决方案

导入依赖
在 pom.xml 中添加上 spring-boot-starter-web 的依赖即可

自定义异常
在应用开发过程中，除系统自身的异常外，不同业务场景中用到的异常也不一样，为了与标题 轻松搞定全局异常 更加的贴切，定义个自己的异常，看看如何捕获…

异常信息模板
定义返回的异常信息的格式，这样异常信息风格更为统一

控制层
仔细一看是不是和平时正常写的代码没啥区别，不要急，接着看….

异常处理（关键）
注解概述

@ControllerAdvice 捕获 Controller 层抛出的异常，如果添加 @ResponseBody 返回信息则为JSON 格式。
@RestControllerAdvice 相当于 @ControllerAdvice 与 @ResponseBody 的结合体。
@ExceptionHandler 统一处理一种类的异常，减少代码重复率，降低复杂度。
创建一个 GlobalExceptionHandler 类，并添加上 @RestControllerAdvice 注解就可以定义出异常通知类了，然后在定义的方法中添加上 @ExceptionHandler 即可实现异常的捕捉…