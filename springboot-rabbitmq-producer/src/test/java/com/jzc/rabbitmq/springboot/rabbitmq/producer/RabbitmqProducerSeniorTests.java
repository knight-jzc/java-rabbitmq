package com.jzc.rabbitmq.springboot.rabbitmq.producer;

import com.jzc.rabbitmq.springboot.rabbitmq.producer.config.RabbitMQSeniorConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 高级特性-发送消息（消息可靠性投递）
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/25 15:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class RabbitmqProducerSeniorTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 确认模式：
     * 步骤：
     * 1. 确认模式开启：配置yml文件中publisher-confirm-type: correlated
     * 2. 在rabbitTemplate定义回调函数
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/25 15:36
     */
    @Test
    void testConfirm() {
        // 2.定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 相关配置信息
             * @param b exchange交换机是否接收到消息 false/true
             * @param s 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("confirm模式成功执行");
                if (b) {
                    System.out.println("接收消息成功" + s);
                } else {
                    System.out.println("接收消息失败" + s);
                }
            }
        });
        // 3.发送消息
        rabbitTemplate.convertAndSend(RabbitMQSeniorConfig.EXCHANGE_SENIOR_NAME,"senior.sshh","confirm。。。");
    }

    /**
     * 回退模式:当消息发送给Exchange后，Exchange路由到Queue失败是才会执行ReturnCallBack步骤:
     * 1．开启回退模式: publisher-returns="true"
     * 2．设置Return CallBack
     * 3．设置Exchange处理消息的模式:
     *        1．如果消息没有路由到Queue，则丢弃消息（默认）
     *        2．如果消息没有路由到Queue，返回给消息发送方Return callBack
     *
     * @author zhichao.jiang01@hand-china.com 2022/10/25 15:36
     */
    @Test
    void testReturn() {
        // 2.定义回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("return 执行了。。。。");
                System.out.println(returnedMessage);
            }
        });
        // 3.发送消息
        for(int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(RabbitMQSeniorConfig.EXCHANGE_SENIOR_NAME,"senior.fa","confirm。。。");
        }
    }

}
