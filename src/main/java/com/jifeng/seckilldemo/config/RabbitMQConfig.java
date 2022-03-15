package com.jifeng.seckilldemo.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.tools.ToolProvider;
import java.net.BindException;

@Configuration
public class RabbitMQConfig {

    //fanout模式
    private static final String QUEUE01= "queue_fanout01";
    private static final String QUEUE02= "queue_fanout02";
    private static final String EXCHANGE= "fanoutExchange";

    //direct模式
    private static final String DIRECT_QUEUE01 = "queue_direct01";
    private static final String DIRECT_QUEUE02 = "queue_direct02";
    private static final String DIRECTEXCHANGE= "directExchange";
    private static final String ROUTINGKEY1 = "queue.red";
    private static final String ROUTINGKEY2 = "queue.green";

    // topic模式
    private static final String SECKILL_QUEUE = "seckill_queue";
    private static final String TOPIC_EXCHANGE = "seckill_exchage";

    @Bean
    public Queue queue(){
        return new Queue("queue", true);
    }


    /**
     * fanout模式开始
     * @return
     */
    @Bean
    public Queue queue01(){
        return new Queue(QUEUE01);
    }

    @Bean
    public Queue queue02(){
        return new Queue(QUEUE02);
    }

    /**
     * fanout生成交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE);
    }

    /**
     * fanout模式绑定广播队列到交换机上
     *
     * @return
     */
    @Bean
    public Binding binding01(){
        return BindingBuilder.bind(queue01()).to(fanoutExchange());
    }

    @Bean
    public Binding binding02(){
        return BindingBuilder.bind(queue02()).to(fanoutExchange());
    }


    /**
     * direct模式开始
     * @return
     */
    @Bean
    public Queue directQueue01(){
        return new Queue(DIRECT_QUEUE01);
    }

    @Bean
    public Queue directQueue02(){
        return new Queue(DIRECT_QUEUE02);
    }

    /**
     * direct生成交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECTEXCHANGE);
    }

    /**
     * direct模式绑定广播队列到交换机上
     *
     * @return
     */
    @Bean
    public Binding directBinding01(){

        return BindingBuilder.bind(directQueue01()).to(directExchange()).with(ROUTINGKEY1);
    }

    @Bean
    public Binding directBinding02(){

        return BindingBuilder.bind(directQueue02()).to(directExchange()).with(ROUTINGKEY2);
    }

    /**
     * topic模式
     */
    @Bean
    public Queue secKillQueue(){
        return new Queue(SECKILL_QUEUE);
    }

    /**
     * topic交换机
     */
    @Bean
    public TopicExchange secKillExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * topic队列和交换机绑定
     */
    @Bean
    public Binding secKillBinding(){
        return BindingBuilder.bind(secKillQueue()).to(secKillExchange()).with("seckill.#");
    }



}
