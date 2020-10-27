package cn.cps;


import cn.cps.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class AppTest 
{

    @Autowired
    private Producer producer;


    @Test
    public void contextLoads() throws Exception {
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        producer.send("黑漫狗!!!", data);
        System.in.read();
    }

}
