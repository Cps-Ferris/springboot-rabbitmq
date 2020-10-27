package cn.cps;

import cn.cps.message.DemoMessage;
import cn.cps.producer.DemoProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplicationDealy.class)
public class AppTestDealy
{
    @Autowired
    private DemoProducer demoProducer;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testSyncSend01() throws Exception {
        this.testSyncSendDelay(null);
    }

    @Test
    public void testSyncSend02() throws Exception {
        this.testSyncSendDelay(5000);
    }

    private void testSyncSendDelay(Integer delay) throws Exception {
        DemoMessage message = new DemoMessage();
        message.setId((int)(System.currentTimeMillis() / 1000L));

        this.demoProducer.syncSend(message, delay);

        logger.info("[onDeadMessage][死信队列发送成功][name：{} ，消息内容：{}]", message);
        System.in.read();
    }

}
