package com.canzhen.persiondemo.controller;

import com.canzhen.persiondemo.bean.RabbitBean;
import com.canzhen.persiondemo.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//编写一个Controller类，用于消息发送工作
@RestController
@RequestMapping(value = "/send")
public class RabbitController {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_BOOK_QUEUE, book); 对应 {@link RabbitHandler#listenerAutoAck}
     * this.rabbitTemplate.convertAndSend(RabbitConfig.MANUAL_BOOK_QUEUE, book); 对应 {@link RabbitHandler#listenerManualAck}
     */
    @GetMapping
    public void defaultMessage() {
        RabbitBean rabbitBean = new RabbitBean();
        rabbitBean.setId("1");
        rabbitBean.setName("一起来学Spring Boot");
        this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_BOOK_QUEUE, rabbitBean);
        this.rabbitTemplate.convertAndSend(RabbitConfig.MANUAL_BOOK_QUEUE, rabbitBean);
    }
}
