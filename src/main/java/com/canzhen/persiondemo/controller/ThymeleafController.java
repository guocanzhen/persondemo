package com.canzhen.persiondemo.controller;

import com.canzhen.persiondemo.bean.Author;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/* 用来映射HTTP请求与页面的跳转 */
@Controller
@RequestMapping("/author")
public class ThymeleafController {
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        // 设置跳转的视图 默认映射到 src/main/resources/templates/{viewName}.html
        view.setViewName("index");
        // 设置属性
        view.addObject("title", "个人简介");
        view.addObject("desc", "欢迎进入 persiondemo 系统");
        Author author = new Author();
        author.setName("郭灿镇");
        author.setAge(25);
        author.setEmail("2589945540@qq.com");
        view.addObject("author", author);
        return view;
    }

    @GetMapping("/index1")
    public String index1(HttpServletRequest request) {
        // TODO 与上面的写法不同，但是结果一致。
        // 设置属性
        request.setAttribute("title", "个人简介");
        request.setAttribute("desc", "欢迎进入 persiondemo 系统");
        Author author = new Author();
        author.setAge(25);
        author.setEmail("2589945540@qq.com");
        author.setName("郭灿镇");
        request.setAttribute("author", author);
        // 返回的 index 默认映射到 src/main/resources/templates/xxxx.html
        return "/index";
    }
}
