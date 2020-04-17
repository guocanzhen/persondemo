package com.canzhen.persiondemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//访问实例
@RestController
@RequestMapping
public class Demo1 {
    @GetMapping("/demo1")
    public String demo1() {
        return "Hello,demo1.";
    }
}
