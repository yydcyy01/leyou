package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;

/**
 * @author YYDCYY
 * @create 2019-10-23
 */
public interface IBrandService {
    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sottBy
     * @param desc
     * @return
     */
      PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);


}
