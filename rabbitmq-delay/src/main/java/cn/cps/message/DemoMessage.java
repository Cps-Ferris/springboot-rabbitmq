package cn.cps.message;

import java.io.Serializable;

public class DemoMessage implements Serializable{

    private static final long serialVersionUID = -638297252657319123L;

    public static final String QUEUE = "queue"; // 正常队列
    public static final String DEAD_QUEUE = "dead_queue"; // 延迟队列（死信队列）

    public static final String EXCHANGE = "exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";

    public static final String ROUTING_KEY = "routing_key"; // 正常路由键
    public static final String DEAD_ROUTING_KEY = "dead_routing_key"; // 延迟路由键（死信路由键）

    /**
     * 编号
     */
    private Integer id;

    public DemoMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo08Message{" +
                "id=" + id +
                '}';
    }

}
