package com.jzc.rabbitmq.springboot.rabbitmq.producer.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 高级特性（延迟队列）-配置类
 * ttl+dlx
 *
 * 1．声明正常的队列(boot_delay_queue)和交换机(boot_delay_exchange)
 * 2．声明死信队列(delay_queue)和死信交换机(delay_exchange)
 * 3．正常队列绑定死信交换机
 *    设置两个参数:
 *        x-dead-letter-exchange:死信交换机名称
 *        x-dead-letter-routing-key:发送给死信交换机的routingkey
 *        x-message-ttl:设置延迟时间
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/28 10:04
 */
@Configuration
public class DelaySeniorConfig {

    public static final String EXCHANGE_F_DELAY_NAME = "boot_delay_exchange";
    public static final String EXCHANGE_DELAY_NAME = "delay_exchange";
    public static final String QUEUE_F_DELAY_NAME = "boot_delay_queue";
    public static final String QUEUE_DELAY_NAME = "delay_queue";

    /**
     * 正常交换机
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/28 10:31
     */
    @Bean("bootDelayExchange")
    public Exchange bootDelayExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_F_DELAY_NAME).durable(true).build();
    }

    /**
     * 死信交换机
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/28 10:31
     */
    @Bean("delayExchange")
    public Exchange delayExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_DELAY_NAME).durable(true).build();
    }

    /**
     * 正常队列绑定死信交换机
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/28 10:31
     */
    @Bean("bootDelayQueue")
    public Queue bootDelayQueue(){
        Map<String,Object> dlxMap = new HashMap<>(16);
        //x-message-ttl 指的是过期时间(作用为延迟时间)
        dlxMap.put("x-message-ttl",10000);
        //x-dead-letter-exchange 指的是死信交换机
        dlxMap.put("x-dead-letter-exchange", DelaySeniorConfig.EXCHANGE_DELAY_NAME);
        //x-dead-letter-routing-key 指的是死信routingKey
        dlxMap.put("x-dead-letter-routing-key","delay.hehe");
        return QueueBuilder.durable(QUEUE_F_DELAY_NAME).withArguments(dlxMap).build();
    }

    /**
     * 死信队列
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/28 10:31
     */
    @Bean("delayQueue")
    public Queue delayQueue(){
        return QueueBuilder.durable(QUEUE_DELAY_NAME).build();
    }

    /**
     *正常队列和交换机绑定关系
     * 1. 知道哪个队列
     * 2. 知道哪个交换机
     * 3. routing key
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/28 10:32
     */
    @Bean
    public Binding bindBootDelayQueueExchange(@Qualifier("bootDelayQueue") Queue queue,@Qualifier("bootDelayExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("boot.delay.#").noargs();
    }


    /**
     *死信队列和死信交换机绑定关系
     * 1. 知道哪个队列
     * 2. 知道哪个交换机
     * 3. routing key
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/28 10:32
     */
    @Bean
    public Binding bindDelayQueueExchange(@Qualifier("delayQueue") Queue queue,@Qualifier("delayExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("delay.#").noargs();
    }
}
