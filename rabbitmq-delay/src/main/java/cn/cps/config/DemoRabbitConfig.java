package cn.cps.config;

import cn.cps.message.DemoMessage;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DemoRabbitConfig {

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Delay Queue
        @Bean
        public Queue getQueue() {
            // 方式一
            return QueueBuilder.durable(DemoMessage.QUEUE) // durable: 是否持久化
                    .exclusive() // exclusive: 是否排它
                    .autoDelete() // autoDelete: 是否自动删除
                    //.ttl(10 * 1000) // ttl 整个队列的过期时间
                    .deadLetterExchange(DemoMessage.EXCHANGE)
                    .deadLetterRoutingKey(DemoMessage.DEAD_ROUTING_KEY)
                    .build();
            // 方式二
//            Map<String, Object> args = new HashMap<>(2);
////       x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
//            args.put("x-dead-letter-exchange", DemoMessage.DEAD_EXCHANGE);
////       x-dead-letter-routing-key  这里声明当前队列的死信路由key
//            args.put("x-dead-letter-routing-key", DemoMessage.DEAD_ROUTING_KEY);
//            return QueueBuilder.durable(DemoMessage.QUEUE) // durable: 是否持久化
//                    .withArguments(args) // exclusive: 是否排它
//                    .ttl(10 * 1000) // 设置队列里的默认过期时间为 10 秒
//                    .build();
        }

        // 创建 Dead Queue
        @Bean
        public Queue getDeadQueue() {
            return new Queue(DemoMessage.DEAD_QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Delay Direct Exchange
        @Bean
        public DirectExchange getExchange() {
            return new DirectExchange(DemoMessage.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }


        // 创建 Dead Direct Exchange
        @Bean
        public DirectExchange getDeadExchange() {
            return new DirectExchange(DemoMessage.DEAD_EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Delay Binding
        // Exchange：DemoMessage.EXCHANGE
        // Routing key：DemoMessage.ROUTING_KEY
        // Queue：DemoMessage.QUEUE
        @Bean
        public Binding demoBinding() {
            return BindingBuilder.bind(getQueue()).to(getExchange()).with(DemoMessage.ROUTING_KEY);
        }

        // 创建 Dead Binding
        // Exchange：DemoMessage.EXCHANGE
        // Routing key：DemoMessage.DELAY_ROUTING_KEY
        // Queue：DemoMessage.DELAY_QUEUE
        @Bean
        public Binding demoDeadBinding() {
            return BindingBuilder.bind(getDeadQueue()).to(getDeadExchange()).with(DemoMessage.DEAD_ROUTING_KEY);
        }

    }

}
