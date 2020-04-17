**SpringBoot 2.x | 第十七篇：轻松搞定文件上传**

1、文件上传
文件上传和下载是JAVA WEB中常见的一种操作，文件上传主要是将文件通过IO流传输到服务器的某一个特定的文件夹下；刚开始工作那会一个上传文件常常花费小半天的时间，繁琐的代码量以及XML配置让我是痛不欲生；值得庆幸的是有了Spring Boot短短的几句代码就能实现文件上传与本地写入操作….

2、导入依赖
在 pom.xml 中添加上 spring-boot-starter-web 和 spring-boot-starter-thymeleaf 的依赖

3、配置文件
默认情况下 Spring Boot 无需做任何配置也能实现文件上传的功能，但有可能因默认配置不符而导致文件上传失败问题，所以了解相关配置信息更有助于我们对问题的定位和修复；

4、具体代码
上传页面
在 src/main/resources 目录下新建 static 目录和 templates 目录。在 templates 中新建一个 index.html 的模板文件；此处实现 单文件上传、多文件上传、BASE64编码 三种上传方式，其中 BASE64 的方式在对Android/IOS/H5等方面还是不错的…

控制层
创建一个FileUploadController，其中@GetMapping的方法用来跳转index.html页面，而@PostMapping相关方法则是对应的 单文件上传、多文件上传、BASE64编码 三种处理方式。
@RequestParam("file") 此处的"file"对应的就是html 中 name="file" 的 input 标签，而将文件真正写入的还是借助的commons-io中的FileUtils.copyInputStreamToFile(inputStream,file)

测试
完成准备事项后，启动Chapter16Application，访问 http://localhost:8080/uploads 进入到文件上传页面。单文件上传、多文件上传都是及其简单的就不做演示了，相信各位自己也是可以完成的。

BASE64 测试方法

打开浏览器访问 http://base64.xpcha.com/pic.html 选择一张图片将其转换为base64编码的，随后将转换后的base64字符串内容 复制到下图中的文本框中，点击上传即可，随后到指定目录下就可以看到我们上传的文件了




