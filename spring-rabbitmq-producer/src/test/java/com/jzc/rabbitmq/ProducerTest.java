package com.jzc.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * spring整合rabbitmq-发送消息
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/24 18:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {

    @Autowired
    //注入rabbitTemplate
    private RabbitTemplate rabbitTemplate;

    //发送普通消息
    @Test
    public void testHelloWorld() {

    rabbitTemplate.convertAndSend("spring_queue","hello world.....");

    }

    //发送广播消息
    @Test
    public void testFanout() {

        rabbitTemplate.convertAndSend("spring_fanout_exchange","","hello fanout.....");

    }

    //发送通配符消息
    @Test
    public void testTopic() {
        rabbitTemplate.convertAndSend("spring_topic_exchange","jzc.hh.ee","hello topic.....");

    }

}
