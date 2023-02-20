package com.jzc.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * RoutingKey模式（路由）-发送消息
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/21 15:57
 */
public class ProducerRouting {

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
        Connection connection = connectionFactory.newConnection();
        //4.创建Channel
        Channel channel = connection.createChannel();
        //5.创建交换机
        /*
        exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
          1.exchange：交换机名称
          2.type：交换机类型
             DIRECT("direct")：定向
             FANOUT("fanout")：扇形（广播）
             TOPIC("topic")：通配符的方式
             HEADERS("headers")：参数匹配
          3.durable：是否持久化
          4.autoDelete：自动删除
          5.internal：内部使用，一般使用false
          6.arguments：参数
         */
        String exchangeName = "test_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,false,null);
        //6.创建队列
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
        //创建队列，没有才会创建
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);
        //7.绑定队列和交换机
        /*
        queueBind(String queue, String exchange, String routingKey)
        参数：
            1.queue：队列名称
            2.exchange：交换机名称
            3.routingKey：路由键，绑定规则
              如果交换机绑定的是fanout，设置为""
         */
        //队列一绑定：error
        channel.queueBind(queue1Name,exchangeName,"error");
        //队列二绑定：error，info，warning
        channel.queueBind(queue2Name,exchangeName,"info");
        channel.queueBind(queue2Name,exchangeName,"error");
        channel.queueBind(queue2Name,exchangeName,"warning");
        /*
         basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
           1.exchange: 交换机名称。简单模式会使用默认的""
           2.routingKey：路由键名称要和对应的队列名称一样才能路由到
           3.props：配置信息
           4.body：主要发送体
         */
        //8.发送消息
        String body = "日志信息：张三调用了delete方法。。报错。日志级别：error。。。";
        channel.basicPublish(exchangeName, "error", null, body.getBytes(StandardCharsets.UTF_8));
        //9.释放资源
        channel.close();
        connection.close();

    }

}
