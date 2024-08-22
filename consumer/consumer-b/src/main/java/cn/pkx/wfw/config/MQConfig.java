package cn.pkx.wfw.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    @Bean
    public DefaultMQProducer defaultMQProducer(){
        return new DefaultMQProducer("null");
    }
}
