package com.jzc.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 订阅模式（广播）-接收消息2
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/24 9:57
 */
public class ConsumerPubSub2 {

    public static void main(String[] args) throws IOException, TimeoutException {

        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2.设置参数
        // ip 默认localhost
        connectionFactory.setHost("172.26.41.194");
        // amqp端口默认5672
        connectionFactory.setPort(5672);
        // 虚拟机名称 默认/
        connectionFactory.setVirtualHost("/hzero");
        // 用户 默认guest
        connectionFactory.setUsername("admin");
        // 密码 默认guest
        connectionFactory.setPassword("admin");
        //3.创建连接 Connection
        Connection connection =connectionFactory.newConnection();
        //4.创建Channel
        Channel channel =connection.createChannel();
        String queue2Name = "test_fanout_queue2";
        /*
         basicConsume(String queue,boolean autoAck,Consumer consumer)
           1.queue: 队列名称。
           2.autoAck: 是否自动确认
           3.consumer：回调对象
         */
        //6.接收消息
        Consumer consumer =new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] body) {
                System.out.println("body:"+new String(body));
                System.out.println("将日志保存至数据库");
            }
        };
        channel.basicConsume(queue2Name,true,consumer);

    }

}
