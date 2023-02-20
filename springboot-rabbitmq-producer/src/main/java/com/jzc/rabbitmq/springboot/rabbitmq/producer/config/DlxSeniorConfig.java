package com.jzc.rabbitmq.springboot.rabbitmq.producer.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 高级特性（死信队列）-配置类
 *
 * 1．声明正常的队列(boot_dlx_queue)和交换机(boot_dlx_exchange)
 * 2．声明死信队列(dlx_queue)和死信交换机(dlx_exchange)
 * 3．正常队列绑定死信交换机
 *    设置两个参数:
 *        x-dead-letter-exchange:死信交换机名称
 *        x-dead-letter-routing-key:发送给死信交换机的routingkey
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/27 14:04
 */
@Configuration
public class DlxSeniorConfig {

    public static final String EXCHANGE_F_DLX_NAME = "boot_dlx_exchange";
    public static final String EXCHANGE_DLX_NAME = "dlx_exchange";
    public static final String QUEUE_F_DLX_NAME = "boot_dlx_queue";
    public static final String QUEUE_DLX_NAME = "dlx_queue";

    /**
     * 正常交换机
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/27 15:31
     */
    @Bean("bootDlxExchange")
    public Exchange bootDlxExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_F_DLX_NAME).durable(true).build();
    }

    /**
     * 死信交换机
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/27 15:31
     */
    @Bean("dlxExchange")
    public Exchange dlxExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_DLX_NAME).durable(true).build();
    }

    /**
     * 正常队列绑定死信交换机
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/27 15:31
     */
    @Bean("bootDlxQueue")
    public Queue bootDlxQueue(){
        Map<String,Object> dlxMap = new HashMap<>(16);
        //x-message-ttl 指的是过期时间
        dlxMap.put("x-message-ttl",10000);
        //x-max-length  指的是队列最大数
        dlxMap.put("x-max-length",10);
        //x-dead-letter-exchange 指的是死信交换机
        dlxMap.put("x-dead-letter-exchange",DlxSeniorConfig.EXCHANGE_DLX_NAME);
        //x-dead-letter-routing-key 指的是死信routingKey
        dlxMap.put("x-dead-letter-routing-key","dlx.hehe");
        return QueueBuilder.durable(QUEUE_F_DLX_NAME).withArguments(dlxMap).build();
    }

    /**
     * 死信队列
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/27 15:31
     */
    @Bean("dlxQueue")
    public Queue dlxQueue(){
        return QueueBuilder.durable(QUEUE_DLX_NAME).build();
    }

    /**
     *正常队列和交换机绑定关系
     * 1. 知道哪个队列
     * 2. 知道哪个交换机
     * 3. routing key
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/27 15:32
     */
    @Bean
    public Binding bindBootDlxQueueExchange(@Qualifier("bootDlxQueue") Queue queue,@Qualifier("bootDlxExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("boot.dlx.#").noargs();
    }


    /**
     *死信队列和死信交换机绑定关系
     * 1. 知道哪个队列
     * 2. 知道哪个交换机
     * 3. routing key
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/27 15:32
     */
    @Bean
    public Binding bindDlxQueueExchange(@Qualifier("dlxQueue") Queue queue,@Qualifier("dlxExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("dlx.#").noargs();
    }
}
