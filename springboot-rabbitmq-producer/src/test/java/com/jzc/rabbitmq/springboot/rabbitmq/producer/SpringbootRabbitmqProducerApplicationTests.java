package com.jzc.rabbitmq.springboot.rabbitmq.producer;

import com.jzc.rabbitmq.springboot.rabbitmq.producer.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringbootRabbitmqProducerApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSend() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"boot.hh","hello springboot rabbitmq");
    }

}
