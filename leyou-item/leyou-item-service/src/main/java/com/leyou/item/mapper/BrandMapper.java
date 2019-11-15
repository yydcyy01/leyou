package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-10-23
 */
public interface BrandMapper extends Mapper<Brand>, SelectByIdListMapper<Brand,Long>  {
    /**
     * 新增商品分类和品牌中间表数据
     * @param cid 商品分类id
     * @param bid 品牌id
     * @return
     */
    @Insert("insert into tb_category_brand (category_id, brand_id) VALUES(#{cid}, #{bid});")
    void insertCategoryAndBrand(
            @Param("cid") Long cid,
            @Param("bid") Long bid);


    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandByCid(
            @Param("cid") Long cid);

    /**
     * 自行添加
     * 2019年11月14日22:15:36
     * @param cid
     * @return
     */
   @Select("select brand_id from tb_category_brand where category_id=#{cid}")
    List<Long> selectBidByCid(@Param("cid") Long cid);


    @Delete("delete from tb_category_brand where brand_id=#{id}")
    void deleteCidByBid(@Param("id") Long id);
    }
