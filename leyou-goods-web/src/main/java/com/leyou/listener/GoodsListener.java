package com.leyou.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author YYDCYY
 * @create 2019-11-14
 */
@Component
public class GoodsListener {
    // 注 , 应该是我还没实现的 . 先放着. 待完成
    @Autowired
    private SearchService searchService;

    public void listenCreate(Long id){
        if (id == null)
            return ;

        searchService
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.item.delete.queue", durable = "true"),
            exchange = @Exchange(value = "leyou.item.exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
    ))
    public void save(Long id){
        if (id == null)
            return;

        searchService.saveIndex(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.item.delete.queue", durable = "true"),
            exchange = @Exchange(value = "leyou.item.exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void delete(Long id){
        if (id == null)
            return;

        searchService.deleteIndex(id);
    }
}
