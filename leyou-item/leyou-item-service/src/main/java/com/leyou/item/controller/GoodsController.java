package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.impl.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 我们把与商品相关的一切业务接口都放到一起，起名为GoodsController，业务层也是这样
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * queryGoodsByPage
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")/*参数列表 : key=&saleable=true&page=1&rows=5 格式传来的*/
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows
    ){
        PageResult<SpuBo> pageResult = this.goodsService.querySpuBoByPage(key, saleable, page, rows);
        if(CollectionUtils.isEmpty(pageResult.getItems())){
            //404
            return ResponseEntity.notFound().build();

        }
        //200
        return ResponseEntity.ok(pageResult);
    }

   /* @GetMapping("sku/page")
    public ResponseEntity<PageResult<Sku>> queryGoodsByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "12") Integer rows)
    {
        PageResult<Sku> pageResult = goodsService.querySeckillSkuByPage(page,rows);
        if(pageResult == null || pageResult.getItems() == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(pageResult);
    }*/

    /**
     * 新增商品
     * 包括，新增spu信息，spuDetial信息
     * 新增sku信息，库存信息
     * http://api.leyou.com/api/item/goods
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        this.goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 编写后台，实现修改商品接口
     * - 请求方式：PUT
     * - 请求路径：/
     *  修改商品-》先删除原有的商品sku和stock，再新增，最后修改spu
     * @param spuBo
     * @return
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.update(spuBo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据spuId查询spuDetail
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId")Long spuId){
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        if (spuDetail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }

    /**
     * - 请求方式：Get
     * - 请求路径：/sku/list
     * - 请求参数：id，应该是spu的id
     * - 返回结果：sku的集合
     * 商品修改时的回显
     * 根据SpuId查询Sku作为回显数据
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkusBySpuId(@RequestParam("id")Long spuId){
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        if (CollectionUtils.isEmpty(skus)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * 展示购物车页时发起的查询,用于检查商品最新价格、是否下架、库存
     * @param skuIds  购物车商品id集合
     */
    /*@GetMapping("skus")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("skuIds")List<Long> skuIds){
        List<Sku> skus = goodsService.querySkuBySpuIds(skuIds);
        return ResponseEntity.ok(skus);
    }

    @GetMapping("sku/{id}")
    public ResponseEntity<Sku> querySkuById(@PathVariable("id")Long skuId){
        Sku sku= this.goodsService.querySkuById(skuId);
        if(sku==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(sku);
    }

    @PutMapping("spu/{spuId}")
    public ResponseEntity<Void> changeSaleable(@PathVariable("spuId")Long spuId){
        try {
            goodsService.changeSaleable(spuId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    /**
     * 删除商品
     * @param spuId
     * @return
     */
   /* @DeleteMapping("spu/{spuId}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("spuId")Long spuId){
        try {
            goodsService.deleteGoods(spuId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuBySpuId(@PathVariable("id")Long spuId){
        Spu spu=this.goodsService.querySpuBySpuId(spuId);
        if(spu==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(spu);
    }*/

    /**
     * 减库存接口
     * CartDTO 没实现
     */
  /*  @PostMapping("stock/decrease")
    public ResponseEntity<Void> decreaseStock(@RequestBody List<CartDTO> carts){
        goodsService.decreaseStock(carts);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
}
