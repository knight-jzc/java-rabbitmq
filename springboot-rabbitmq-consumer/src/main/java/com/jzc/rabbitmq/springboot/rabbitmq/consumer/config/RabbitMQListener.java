package com.jzc.rabbitmq.springboot.rabbitmq.consumer.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * springboot-rabbitmq-监听类
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/25 13:35
 */
@Component
public class RabbitMQListener {

    @RabbitListener(queues = "boot_queue")
    public void listenerQueue(Message message){
        System.out.println(new String(message.getBody()));
    }

    /**
     * Consumer ACK机制:
     * 1．设置手动签收。listener:
     *                  direct:
     *                    acknowledge-mode: manual(yml文件设置)
     * 2．@RabbitListener添加ackMode模式为MANUAL
     * 3．如果消息成功处理，则调用channel的basicAck()签收
     * 4．如果消息处理失败，则调用channel的basicNack()拒绝签收，broker重新发送给consumer
     *
     */
    @RabbitListener(queues = "boot_senior_queue",ackMode = "MANUAL")
    public void listenerSeniorQueue(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //1.接收转换消息
            System.out.println(new String(message.getBody()));

            //2.处理业务逻辑
            System.out.println("处理业务逻辑。。。");
            //假如出错
            //int a=3/0;
            //3.手动签收消息
            channel.basicAck(deliveryTag, true);
        }catch (Exception e){
            //4.拒绝签收
            /*
            第三个参数requeue：重回队列。如果设置为true，则消息重新回到queue，broker会重新发送该消息给消费端
             */
            channel.basicNack(deliveryTag,true,true);
        }
    }

}
