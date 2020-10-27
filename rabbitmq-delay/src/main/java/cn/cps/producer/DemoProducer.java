package cn.cps.producer;

import cn.cps.message.DemoMessage;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DemoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(DemoMessage demoMessage, Integer delay) {
        // 创建 DemoMessage 消息
        // 同步发送消息
        rabbitTemplate.convertAndSend(DemoMessage.EXCHANGE, DemoMessage.ROUTING_KEY, demoMessage, new MessagePostProcessor() {

            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置消息的 TTL 过期时间
                if (delay != null && delay > 0) {
                    message.getMessageProperties().setExpiration(String.valueOf(delay)); // Spring-AMQP API 设计有问题，所以传入了 String = =
                }
                message.getMessageProperties().setHeader("name","Ferris");
                return message;
            }

        });
    }

}
