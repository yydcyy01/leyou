package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-11-15
 */
public interface ICategoryService {
    List<Category> queryByParentId(Long parentId);

    List<String> queryNamesByIds(List<Long> ids);

    List<Category> queryCategoryByBid(Long bid);
}
