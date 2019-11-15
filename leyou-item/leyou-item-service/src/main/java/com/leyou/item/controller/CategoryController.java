package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-10-22
 * 提供分类的增删改查接口
 * 操作的表
 * tb_category
 * tb_category_brand
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 根据父节点 id 查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam(value = "pid",defaultValue = "0") Long pid){
        System.out.println("pid: " + pid);
        try {
            // 400 : 参数不合法

            if (pid == null || pid.longValue() < 0) {
                // 最初写法 :
                // return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

                //优化/简化写法①
                //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

                //优化/简化写法②
                System.out.println(400);
                return ResponseEntity.badRequest().build();
            }

            List<Category> categories =  this.categoryService.queryByParentId(pid);

            //404 资源服务器未找到
            // 相当于 if (categories == null || categories.size() == 0) 3 年工作经验
            if (CollectionUtils.isEmpty(categories)){
                System.out.println(404);
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                return ResponseEntity.notFound().build();
            }
            //200  查询成功
            System.out.println(200);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 500 服务器内部错误  这个可省略, 因为错误本来就是 500 => try()- catcy() 就不需要了
        System.out.println(500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){

        List<String> names = this.categoryService.queryNamesByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    /**
     * 根据品牌id查询分类
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBid(@PathVariable("bid")Long bid){
        List<Category> categories=categoryService.queryCategoryByBid(bid);
        if(categories.size()==0||categories==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(categories);
    }

}
