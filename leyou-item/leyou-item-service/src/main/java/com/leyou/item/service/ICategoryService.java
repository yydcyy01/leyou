package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> queryByParentId(Long parentId);
    public List<String> queryNamesByIds(List<Long> ids);
}
