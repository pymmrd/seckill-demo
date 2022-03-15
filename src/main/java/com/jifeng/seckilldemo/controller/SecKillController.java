package com.jifeng.seckilldemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jifeng.seckilldemo.entity.*;
import com.jifeng.seckilldemo.rabbitmq.MQSender;
import com.jifeng.seckilldemo.service.IGoodsService;
import com.jifeng.seckilldemo.service.IOrderService;
import com.jifeng.seckilldemo.service.ISeckillOrderService;
import com.jifeng.seckilldemo.vo.GoodsVo;
import com.jifeng.seckilldemo.vo.RespBean;
import com.jifeng.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MQSender mqSender;

    @RequestMapping("/doSecKill")
    public RespBean doSecKill(Model model, User user , Long goodsId){
        if(user == null){
            return RespBean.error(RespBeanEnum.ERROR);
        }
        //model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 查看是否重复下单
        SeckillOrder seckillOrder =
                (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId );
        if (seckillOrder != null){
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }
        // 预减库存
        Long stock = valueOperations.decrement("seckillGoods:" + goodsId);
        if(stock < 0 ){
            valueOperations.increment("seckillGoods:" + goodsId);
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }
        SecKillMessage secKillMessage = new SecKillMessage();
        secKillMessage.setUser(user);
        secKillMessage.setGoodsID(goodsId);
        mqSender.seckillsend(JSONObject.toJSONString(secKillMessage));
        return RespBean.success(0);


        /*
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        // 判断库存
        if(goodsVo.getStockCount() < 1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        // 判断订单是否重复
        QueryWrapper<SeckillOrder> qw = new QueryWrapper<>();
        qw.eq("user_id", user.getId()).eq("goods_id", goodsId);
        SeckillOrder seckillOrder = seckillOrderService.getOne(qw);
        if (seckillOrder != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "seckKillFail";
        }
        Order order = orderService.secKill(user, goodsVo);
        model.addAttribute("order", order);
        model.addAttribute("goods", goodsVo);
         */
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        list.forEach(goodsVo -> {
            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
        });
    }
}
