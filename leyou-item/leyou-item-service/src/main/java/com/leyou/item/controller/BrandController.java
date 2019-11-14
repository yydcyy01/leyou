package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.IBrandService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-10-23
 */
@RequestMapping("brand")
@RestController
public class BrandController {
    @Autowired
    private IBrandService brandService;

    //处理请求

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param soryBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity <PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "soryBy", required = false) String soryBy,
            @RequestParam(value = "desc", required = false) Boolean desc){

        PageResult<Brand> result = this.brandService.queryBrandsByPage(key, page, rows, soryBy, desc);
        if (CollectionUtils.isEmpty(result.getItems()))
            return ResponseEntity.notFound().build();


        return ResponseEntity.ok(result);
    }

    /**
     * 对品牌进行新增
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        brandService.saveBrand(brand, cids);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 商品管理/ 商品列表 / 新增管理页面
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid")Long cid){
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }

    /**
     * 对品牌进行修改
     * @param brand
     * @param cids
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        brandService.updateBrand(brand, cids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 对品牌进行删除
     * @param bid
     * @return
     */
    @DeleteMapping("{bid}")
    public ResponseEntity<Void> deleteBrandByBid(@PathVariable("bid")Long bid){
        brandService.deleteBrandByBid(bid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandsByIds(@RequestParam("ids") List<Long> ids){
        List<Brand>brands=brandService.queryBrandsByIds(ids);
        if(brands==null||brands.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(brands);
    }
}
