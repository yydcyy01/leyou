package com.leyou.item.bo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * 为啥叫 Bo ? 因为是业务对象. 啥都没.
 * 前端页面中 : src/pages/item/Goods.vue
 * "
 *   <td class="text-xs-center">{{props.item.cname}}</td>
 *   <td class="text-xs-center">{{ props.item.bname }}</td>
 *   调用的就是这个 Bo 类进行渲染( 页面显示 )
 *
 *   Spu的json格式的对象，spu中包含spuDetail和Sku集合。这里我们该怎么接收？
 *   我们之前定义了一个SpuBo对象，作为业务对象。
 *   这里也可以用它，不过需要在原有 SpuBo 类基础上再扩展[ 添加 ]spuDetail和skus字段
 * "
 */
public class SpuBo extends Spu {
    String cname;// 商品分类名称
    String bname;// 品牌名称
    SpuDetail spuDetail;// 商品详情
    List<Sku> skus;// sku列表


    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }


    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

}