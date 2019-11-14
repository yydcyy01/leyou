package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author YYDCYY
 * @create 2019-11-04
 */

/**
 * 商品的FeignClient：
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
