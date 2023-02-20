package com.jzc.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * spring整合rabbitmq-接收消息测试类
 *
 * @author zhichao.jiang01@hand-china.com 2022/10/24 19:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-consumer.xml")
public class ConsumerTest {
    @Test
    public void test() {
        boolean falg=true;
        while (true){

        }
    }
}
