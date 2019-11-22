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

import java.util.ArrayList;
import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-10-23
 *
 * 商标增删改 4 种查方式
 *  2019年11月14日23:45:50
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

       // System.out.println("desc : " + desc);
        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy))
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
       // System.out.println("sortBy: " + sortBy);
        List<Brand> brands = this.brandMapper.selectByExample(example);

        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }


    public List<Brand> queryBrandsByCid(Long cid) {

        return this.brandMapper.selectBrandByCid(cid);
    }

    @Override
    public List<Brand> queryBrandByCid(Long cid) {
        List<Long> bids=brandMapper.selectBidByCid(cid);
        List<Brand> brands = new ArrayList<>();
        bids.forEach(bid->
                brands.add(brandMapper.selectByPrimaryKey(bid))
        );
        return brands;
    }
    @Override
    public String queryBrandNameByBid(Long brandId) {
        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        return brand.getName();
    }

    @Override
    public List<Brand> queryBrandByIds(List<Long> ids) {

        return  brandMapper.selectByIdList(ids);
    }

    /**
     * 新增品牌，还要维护品牌和商品分类的中间表。
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        // 先新增brand
        this.brandMapper.insertSelective(brand);

        // 在新增中间表
        cids.forEach(cid -> {
            this.brandMapper.insertCategoryAndBrand(cid, brand.getId());
        });
        // 两种写法一样
        /*for (Long cid : cids){
            Long bid = brand.getId();
            this.brandMapper.insertCategoryAndBrand(cid, bid);
        }*/
    }

    @Override
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        brandMapper.updateByPrimaryKey(brand);
        brandMapper.deleteCidByBid(brand.getId());
        for (Long cid : cids) {
            brandMapper.insertCategoryAndBrand(cid,brand.getId());
        }
    }

    @Override
    @Transactional
    public void deleteBrandByBid(Long bid) {
        Brand brand = new Brand();
        brand.setId(bid);
        brandMapper.delete(brand);
        brandMapper.deleteCidByBid(bid);
    }
}
