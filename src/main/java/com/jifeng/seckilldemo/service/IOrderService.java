package com.jifeng.seckilldemo.service;

import com.jifeng.seckilldemo.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jifeng.seckilldemo.entity.User;
import com.jifeng.seckilldemo.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jifeng
 * @since 2022-03-11
 */
public interface IOrderService extends IService<Order> {

    Order secKill(User user, GoodsVo goodsVo);
}
