package com.jzc.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * workQueue模式-发送消息
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/21 15:57
 */
public class ProducerWorkQueue {

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
         basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
           1.exchange: 交换机名称。简单模式会使用默认的""
           2.routingKey：路由键名称要和对应的队列名称一样才能路由到
           3.props：配置信息
           4.body：主要发送体
         */
        //6.发送消息
        for (int i = 0; i < 10; i++) {
            String body = i+"、hello rabbitmq workQueue";
            channel.basicPublish("","workQueue",null,body.getBytes(StandardCharsets.UTF_8));
        }
        channel.close();
        connection.close();

    }

}
