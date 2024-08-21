package cn.pkx.wfw.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class NacosConfig {
    @Bean
    public ConfigService nacosConfigService() throws NacosException {

        String serverAddr = "localhost:8848";

        ConfigService configService = NacosFactory.createConfigService(serverAddr);


        Properties properties = new Properties();
        properties.setProperty("serverAddr", "127.0.0.1:8848");
        properties.setProperty("namespace", "public");
        properties.setProperty("endpoint", "127.0.0.1");
        properties.setProperty("endpointPort", "8848");
        properties.setProperty("endpointContextPath", "/nacos/");
//        properties.setProperty("clusterName","namespace");
        return configService;
    }
}
