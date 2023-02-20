package com.jzc.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * workQueue模式-接收消息2
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/24 9:57
 */
public class ConsumerWorkQueue2 {

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
        //5.创建队列
        /*
        queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
          1.queue：队列名称
          2.durable：是否持久化，mq重启后是否存在
          3.exclusive：
                  是否独占，只能有一个消费者监听这队列
                  当connection关闭时，是否删除队列
          4.autoDelete：是否自动删除
          5.arguments：其他参数
         */
        //没有才会创建
        channel.queueDeclare("workQueue",true,false,false,null);
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
                System.out.println("consumerTag:"+s);
                System.out.println("routingKey:"+envelope.getRoutingKey());
                System.out.println("exchange:"+envelope.getExchange());
                System.out.println("properties:"+basicProperties);
                System.out.println("body:"+new String(body));
            }
        };
        channel.basicConsume("workQueue",true,consumer);

    }

}
