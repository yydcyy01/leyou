package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.IBrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-10-23
 */
@Service("brandService")
public class BrandServiceImpl implements IBrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key))
            criteria.andLike("name", "%" + "key" + "%").orEqualTo("letter",key);

        // 添加分页条件
        PageHelper.startPage(page, rows);

        System.out.println("desc : " + desc);
        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy))
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        System.out.println("sortBy: " + sortBy);
        List<Brand> brands = this.brandMapper.selectByExample(example);

        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 新增品牌，还要维护品牌和商品分类的中间表。
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        // 先新增brand
        this.brandMapper.insertSelective(brand);

        // 在新增中间表
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
    }


}
