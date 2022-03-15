package com.jifeng.seckilldemo.service;

import com.jifeng.seckilldemo.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jifeng.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jifeng
 * @since 2022-03-11
 */
public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
