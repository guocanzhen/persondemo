package com.canzhen.persiondemo.controller;

import com.canzhen.persiondemo.annotation.CacheLock;
import com.canzhen.persiondemo.annotation.CacheParam;
import com.canzhen.persiondemo.annotation.LocalLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moreSubmit")
public class BookController {
//    在接口上添加 @LocalLock(key = "book:arg[0]")；
// 意味着会将 arg[0] 替换成第一个参数的值，生成后的新 key 将被缓存起来；
//    @LocalLock(key = "book:arg[0]")
//    @GetMapping
//    public String query(@RequestParam String token) {
//        return "success - " + token;
//    }

    @CacheLock(prefix = "books")
    @GetMapping
    public String query(@CacheParam(name = "token") @RequestParam String token) {
        return "success - " + token;
    }
}
