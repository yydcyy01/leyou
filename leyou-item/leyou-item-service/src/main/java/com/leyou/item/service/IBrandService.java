package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-11-15
 */
public interface IBrandService{
    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    List<Brand> queryBrandByCid(Long cid);

    String queryBrandNameByBid(Long brandId);

    List<Brand> queryBrandByIds(List<Long> ids);

    @Transactional
    void saveBrand(Brand brand, List<Long> cids);

    @Transactional
    void updateBrand(Brand brand, List<Long> cids);

    @Transactional
    void deleteBrandByBid(Long bid);
}
