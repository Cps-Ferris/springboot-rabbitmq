package cn.cps.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String content, CorrelationData data) {
        // 创建 Message 消息
        rabbitTemplate.convertAndSend("boot-topic-exchange","slow.red.dog", content, data);
    }

}
