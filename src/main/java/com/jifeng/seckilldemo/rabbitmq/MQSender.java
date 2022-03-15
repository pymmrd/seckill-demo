package com.jifeng.seckilldemo.rabbitmq;


import com.jifeng.seckilldemo.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object msg){
        log.info("发送消息" +  msg);
        rabbitTemplate.convertAndSend("queue", msg);

    }

    public void sendFanout(Object msg){
        log.info("发送消息" +  msg);
        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
    }

    public void sendDirect01(Object msg){
        log.info("发送red消息" +  msg);
        rabbitTemplate.convertAndSend("directExchange", "queue.red", msg);
    }

    public void sendDirect02(Object msg){
        log.info("发送green消息" +  msg);
        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
    }

    public void seckillsend(String message){
        log.info("发送秒杀消息" + message);
        rabbitTemplate.convertAndSend("seckill_exchage", "seckill.queue",  message);
    }
}
