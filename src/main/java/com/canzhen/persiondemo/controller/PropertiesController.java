package com.canzhen.persiondemo.controller;

import com.canzhen.persiondemo.config.My1Properties;
import com.canzhen.persiondemo.config.MyProperties2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/properties")
@RestController
public class PropertiesController {
    private static final Logger log = LoggerFactory.getLogger(PropertiesController.class);

    private final My1Properties my1Properties;
    private final MyProperties2 myProperties2;
    @Autowired
    public PropertiesController(My1Properties my1Properties,MyProperties2 myProperties2) {
        this.my1Properties = my1Properties;
        this.myProperties2 = myProperties2;
    }

    @GetMapping("/1")
    public My1Properties my1Properties() {
        log.info("=================================================================================================");
        log.info(my1Properties.toString());
        log.info("=================================================================================================");
        return my1Properties;
    }

    @GetMapping("/2")
    public MyProperties2 myProperties2(){
        log.info("=================================================================================================");
        log.info(myProperties2.toString());
        log.info("=================================================================================================");
        return myProperties2;
    }

}
