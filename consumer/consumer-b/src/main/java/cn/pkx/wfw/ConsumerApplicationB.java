package cn.pkx.wfw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConsumerApplicationB {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplicationB.class, args);
    }
}
