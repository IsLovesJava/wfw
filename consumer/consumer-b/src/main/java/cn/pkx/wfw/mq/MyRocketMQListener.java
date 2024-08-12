package cn.pkx.wfw.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}", topic = "wfw")
public class MyRocketMQListener implements RocketMQListener<Object> {
    @Override
    public void onMessage(Object o) {
        System.out.println("message : "+ o );
    }
}
