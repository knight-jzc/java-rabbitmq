package com.jzc.rabbitmq.springboot.rabbitmq.producer;

import com.jzc.rabbitmq.springboot.rabbitmq.producer.config.DlxSeniorConfig;
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
 * 高级特性（dxl死信队列）-发送消息测试类
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/27 16:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class RabbitmqProducerDlxTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送测试死信消息
     * 1.过期时间
     * 2.队列长度限制
     * 3.拒收消息
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/27 16:36
     */
    @Test
    void testDlx() {
        // 1.过期时间
//        rabbitTemplate.convertAndSend(DlxSeniorConfig.EXCHANGE_F_DLX_NAME, "boot.dlx.aaa", "过期时间死信");
        // 2.队列长度限制
//        for (int i = 0; i < 11; i++) {
//            rabbitTemplate.convertAndSend(DlxSeniorConfig.EXCHANGE_F_DLX_NAME, "boot.dlx.fa", "dlx测试");
//        }
        //3. 拒绝签收
        rabbitTemplate.convertAndSend(DlxSeniorConfig.EXCHANGE_F_DLX_NAME, "boot.dlx.ccc", "拒绝签收死信");
    }


}
