package com.leyou.item.bo;

import com.leyou.item.pojo.Spu;

/**
 * 为啥叫 Bo ? 因为是业务对象. 啥都没.
 * 前端页面中 : src/pages/item/Goods.vue
 * "
 *   <td class="text-xs-center">{{props.item.cname}}</td>
 *   <td class="text-xs-center">{{ props.item.bname }}</td>
 *   调用的就是这个 Bo 类进行渲染( 页面显示 )
 * "
 */
public class SpuBo extends Spu {
    String cname;// 商品分类名称

    String bname;// 品牌名称


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