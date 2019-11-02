package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-10-22
 */
public interface CategoryMapper extends Mapper<Category> , SelectByIdListMapper<Category, Long> {

}
