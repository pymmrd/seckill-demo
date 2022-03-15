package com.jifeng.seckilldemo.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jifeng.seckilldemo.entity.SecKillMessage;
import com.jifeng.seckilldemo.entity.SeckillOrder;
import com.jifeng.seckilldemo.entity.User;
import com.jifeng.seckilldemo.service.IGoodsService;
import com.jifeng.seckilldemo.service.IOrderService;
import com.jifeng.seckilldemo.service.ISeckillOrderService;
import com.jifeng.seckilldemo.vo.GoodsVo;
import com.jifeng.seckilldemo.vo.RespBean;
import com.jifeng.seckilldemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues="queue")
    public void receive(Object msg){
        log.info("接收消息" + msg);
    }

    @RabbitListener(queues="queue_fanout01")
    public void receive01(Object msg){
        log.info("QUEUE01接收消息" + msg);
    }

    @RabbitListener(queues="queue_fanout02")
    public void receive02(Object msg){
        log.info("QUEUE02接收消息" + msg);
    }

    @RabbitListener(queues="queue_direct01")
    public void directReceive01(Object msg){
        log.info("QUEUE01接收消息" + msg);
    }

    @RabbitListener(queues="queue_direct02")
    public void directReceive02(Object msg){
        log.info("QUEUE02接收消息" + msg);
    }

    @RabbitListener(queues="seckill_queue")
    public void seckillConsumer(String message){
        log.info("秒杀消费者:" + message);
        SecKillMessage secKillMessage = JSONObject.parseObject(message, SecKillMessage.class);
        Long goodsId = secKillMessage.getGoodsID();
        User user = secKillMessage.getUser();
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        // 判断库存
        if(goodsVo.getStockCount() < 1){
           return ;
        }
        // 判断订单是否重复
        SeckillOrder seckillOrder =
                (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId );
        if (seckillOrder != null){
            return;
        }
        orderService.secKill(user, goodsVo);
        return;

    }
}
