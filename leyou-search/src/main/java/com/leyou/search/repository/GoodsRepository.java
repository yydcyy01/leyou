package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author YYDCYY
 * @create 2019-11-05
 */

/**
 * 继承这个接口, 实现增删改查
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
