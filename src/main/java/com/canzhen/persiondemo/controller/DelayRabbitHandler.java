package com.canzhen.persiondemo.controller;

import com.canzhen.persiondemo.bean.RabbitBean;
import com.canzhen.persiondemo.config.DelayRabbitConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

//默认情况下 spring-boot-data-amqp 是自动ACK机制，就意味着 MQ 会在消息消费完毕后自动帮我们去 ACK，
// 这样依赖就存在这样一个问题：如果报错了，消息不会丢失，会无限循环消费，很容易就吧磁盘空间耗完，
// 虽然可以配置消费的次数但这种做法也有失优雅。
// 目前比较推荐的就是我们手动ACK然后将消费错误的消息转移到其它的消息队列中，做补偿处理。
// 由于我们需要手动控制ACK，因此下面监听完消息后需要调用basicAck通知rabbitmq消息已被正确消费，可以将远程队列中的消息删除
@Component
public class DelayRabbitHandler {
    private static final Logger log = LoggerFactory.getLogger(DelayRabbitHandler.class);

    @RabbitListener(queues = {DelayRabbitConfig.REGISTER_QUEUE_NAME})
    public void listenerDelayQueue(RabbitBean rabbitBean, Message message, Channel channel) {
        log.info("[listenerDelayQueue 监听的消息] - [消费时间] - [{}] - [{}]", LocalDateTime.now(), rabbitBean.toString());
        try {
            // TODO 调用basicAck通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }
}
