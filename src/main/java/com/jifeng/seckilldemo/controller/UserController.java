package com.jifeng.seckilldemo.controller;


import com.jifeng.seckilldemo.rabbitmq.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jifeng
 * @since 2022-03-07
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MQSender mqSender;

    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
        mqSender.send("hello");
    }


    @RequestMapping("/mqfanout")
    @ResponseBody
    public void mqfanout(){
        mqSender.sendFanout("hello");
    }

    @RequestMapping("/mqdirect")
    @ResponseBody
    public void mqdirect(){
        mqSender.sendDirect01("hello");
        mqSender.sendDirect02("green hello");
    }

}
