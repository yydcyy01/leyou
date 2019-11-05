package com.leyou.search.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author YYDCYY
 * @create 2019-11-04
 */

/**
 * 规格参数的FeignClient:
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
