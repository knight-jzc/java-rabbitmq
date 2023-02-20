package com.jzc.rabbitmq.springboot.rabbitmq.producer;

import com.jzc.rabbitmq.springboot.rabbitmq.producer.config.TtlSeniorConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 高级特性（ttl消息过期）-发送消息测试类
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/26 19:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class RabbitmqProducerTtlTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * TTL:过期时间
     * 队列统一过期
     *
     * 2.消息单独过期
     * 如果设置了消息的过期时间，也设置了队列的过期时间，它以时间短的为准。队列过期后，会将队列所有消息全部移除。
     * 消息过期后,只有消息在队列顶端,才会判断其是否过期(移除掉）
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/26 19:36
     */
    @Test
    void testTtl() {
        MessagePostProcessor message=new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置message消息
                //过期时间
                message.getMessageProperties().setExpiration("5000");
                //返回消息
                return message;
            }
        };
        // 3.发送消息
        for(int i = 0; i < 10; i++) {
            if (i == 5) {
                // 单独过期的消息
                rabbitTemplate.convertAndSend(TtlSeniorConfig.EXCHANGE_TTL_NAME, "ttl.fa", "ttl测试", message);
            }else {
                // 不过期消息
                rabbitTemplate.convertAndSend(TtlSeniorConfig.EXCHANGE_TTL_NAME, "ttl.fa", "ttl测试");
            }
        }
    }

}
