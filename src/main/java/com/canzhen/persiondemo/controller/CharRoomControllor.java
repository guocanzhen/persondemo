package com.canzhen.persiondemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/websocket")
public class CharRoomControllor {
//    用于跳转html页面
    @GetMapping
    public String webSocket() {
        return "webSocket";
    }
}
