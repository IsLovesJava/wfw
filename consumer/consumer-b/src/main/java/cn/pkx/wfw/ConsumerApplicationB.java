package cn.pkx.wfw;

import cn.pkx.wfw.mq.annotation.EnableRocketMqConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableRocketMqConsumer
public class ConsumerApplicationB {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplicationB.class, args);
    }
}
