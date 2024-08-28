package cn.pkx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
public class NormalApplication {
    public static void main(String[] args) {
        SpringApplication.run(NormalApplication.class, args);
    }
}