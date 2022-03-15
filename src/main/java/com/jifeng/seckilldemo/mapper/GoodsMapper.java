package com.jifeng.seckilldemo.mapper;

import com.jifeng.seckilldemo.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jifeng.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jifeng
 * @since 2022-03-11
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     *
     * @return
     */
    List<Goods> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
