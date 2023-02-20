package com.jzc.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * spring整合rabbitmq-接收普通消息
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/24 19:12
 */
public class SpringQueueListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        //打印消息
        System.out.println(new String(message.getBody()));
    }
}
