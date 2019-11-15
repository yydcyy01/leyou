package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-11-15
 */
public interface IGoodsService {
    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);

    @Transactional
    void saveGoods(SpuBo spuBo);

    @Transactional
    void update(SpuBo spuBo);

    SpuDetail querySpuDetailBySpuId(Long spuId);

    List<Sku> querySkusBySpuId(Long spuId);
}
