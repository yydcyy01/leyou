package com.leyou.listener;

import org.springframework.stereotype.Component;

/**
 * @author YYDCYY
 * @create 2019-11-14
 */
@Component
public class GoodsListener {



    /**
     * 处理 create 消息
     *
     * @param id
     */
   /* @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.item.create.queue", durable = "true"),
            exchange = @Exchange(value = "leyou.item.exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
    ))
    public void create(Long id){
        if (id == null)
            return;

        searchService.createIndex(id);
    }*/

    /**
     * 处理 insert , update 消息
     *
     * @param id
     */
   /* @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.item.save.queue", durable = "true"),
            exchange = @Exchange(value = "leyou.item.exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
    ))
    public void save(Long id){
        if (id == null)
            return;

        searchService.saveIndex(id);
    }*/

    /**
     * 处理delete的消息
     * @param id
     */
    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.item.delete.queue", durable = "true"),
            exchange = @Exchange(value = "leyou.item.exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void delete(Long id){
        if (id == null)
            return;

        searchService.deleteIndex(id);
    }*/
}
