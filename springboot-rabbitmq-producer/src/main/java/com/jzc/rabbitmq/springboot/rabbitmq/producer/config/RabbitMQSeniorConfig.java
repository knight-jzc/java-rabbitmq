package com.jzc.rabbitmq.springboot.rabbitmq.producer.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 高级特性（消息可靠性投递）-配置类
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/25 10:04
 */
@Configuration
public class RabbitMQSeniorConfig {

    public static final String EXCHANGE_SENIOR_NAME = "boot_senior_exchange";
    public static final String QUEUE_SENIOR_NAME = "boot_senior_queue";

    /**
     * 高级特性测试-交换机
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/25 15:31
     */
    @Bean("bootSeniorExchange")
    public Exchange bootSeniorExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_SENIOR_NAME).durable(true).build();
    }

    /**
     * 高级特性测试-队列
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/25 15:31
     */
    @Bean("bootSeniorQueue")
    public Queue bootSeniorQueue(){
        return QueueBuilder.durable(QUEUE_SENIOR_NAME).build();
    }

    /**
     *队列和交换机绑定关系
     * 1. 知道哪个队列
     * 2. 知道哪个交换机
     * 3. routing key
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/25 15:32
     */
    @Bean
    public Binding bindSeniorQueueExchange(@Qualifier("bootSeniorQueue") Queue queue,@Qualifier("bootSeniorExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("senior.#").noargs();
    }
}
