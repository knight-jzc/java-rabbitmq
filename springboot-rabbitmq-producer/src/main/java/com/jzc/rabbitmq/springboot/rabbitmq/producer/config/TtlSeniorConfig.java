package com.jzc.rabbitmq.springboot.rabbitmq.producer.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 高级特性（ttl消息过期）-配置类
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/26 19:04
 */
@Configuration
public class TtlSeniorConfig {

    public static final String EXCHANGE_TTL_NAME = "boot_ttl_exchange";
    public static final String QUEUE_TTL_NAME = "boot_ttl_queue";

    /**
     * 高级特性测试-交换机
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/25 15:31
     */
    @Bean("bootTtlExchange")
    public Exchange bootTtlExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TTL_NAME).durable(true).build();
    }

    /**
     * 高级特性测试-队列
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/26 19:31
     */
    @Bean("bootTtlQueue")
    public Queue bootTtlQueue(){
        //x-message-ttl 指的是过期时间
        return QueueBuilder.durable(QUEUE_TTL_NAME).withArgument("x-message-ttl",10000).build();
    }

    /**
     *队列和交换机绑定关系
     * 1. 知道哪个队列
     * 2. 知道哪个交换机
     * 3. routing key
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/26 19:32
     */
    @Bean
    public Binding bindTtlQueueExchange(@Qualifier("bootTtlQueue") Queue queue,@Qualifier("bootTtlExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("ttl.#").noargs();
    }
}
