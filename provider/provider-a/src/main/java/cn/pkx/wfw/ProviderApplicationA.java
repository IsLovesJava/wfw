package cn.pkx.wfw;

import cn.pkx.wfw.mq.annotation.EnableRocketMqProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRocketMqProducer
public class ProviderApplicationA {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplicationA.class, args);
    }
}