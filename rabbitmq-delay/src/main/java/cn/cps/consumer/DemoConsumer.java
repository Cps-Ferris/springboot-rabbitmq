package cn.cps.consumer;

import cn.cps.message.DemoMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DemoConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 不能配置监听延迟队列，一旦监听 那么消息就被消费了，也就不存在死信【消息在队列的存活时间超过设置的TTL时间】
//    @RabbitListener(queues = DemoMessage.QUEUE)
//    public void onMessage(DemoMessage message) {
//        logger.info("[onMessage][业务队列][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
//    }

    @RabbitListener(queues = DemoMessage.DEAD_QUEUE)
    public void onDeadMessage(DemoMessage demoMessage, Channel channel, Message message) throws IOException {
        String name = message.getMessageProperties().getHeader("name");
        logger.info("[onDeadMessage][死信队列][name：{} ，消息内容：{}]", name, demoMessage);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
