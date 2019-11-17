package com.leyou.goods.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author YYDCYY
 * @create 2019-11-13
 */
@Controller
public class GoodsController {
    public String toItemPage(@PathVariable("id") Long id){

        return "item";  // 以后会通过 Themyleaf 解析
    }
}
