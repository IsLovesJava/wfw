package cn.pkx.wfw.config;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public LoadBalancerClient loadBalancerClient(LoadBalancerClientFactory loadBalancerClientFactory) {
        return new MyFeignClient(loadBalancerClientFactory);
    }
}
