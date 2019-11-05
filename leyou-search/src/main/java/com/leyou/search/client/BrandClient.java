package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author YYDCYY
 * @create 2019-11-04
 */

/**
 * 品牌的FeignClient：
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {

}
