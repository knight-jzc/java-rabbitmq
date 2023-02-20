package com.jzc.rabbitmq.springboot.rabbitmq.producer;

import com.jzc.rabbitmq.springboot.rabbitmq.producer.config.DelaySeniorConfig;
import com.jzc.rabbitmq.springboot.rabbitmq.producer.config.DlxSeniorConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 高级特性（延迟队列）-发送消息测试类
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/28 10:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class RabbitmqProducerDelaytTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送测试延迟消息
     * 1.过期时间
     *
     * @author zhichao.jiang01@hand-china.com  2022/10/28 10:36
     */
    @Test
    void testDelay() throws InterruptedException {
        // 1.过期时间
        rabbitTemplate.convertAndSend(DelaySeniorConfig.EXCHANGE_F_DELAY_NAME, "boot.delay.ccc", "延迟消息");
        //倒计时
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(1000);
            System.out.println(i);
        }
    }


}
