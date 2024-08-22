package cn.pkx.wfw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplicationC {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplicationC.class,args);
    }
}