package cn.pkx.wfw.config;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;

import java.util.*;

public class MyFeignClient extends BlockingLoadBalancerClient {
    public MyFeignClient(ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerClientFactory) {
        super(loadBalancerClientFactory);
    }

    private static final Map<String, List<ServiceInstance>> SERVICE_INSTANCES = new HashMap<>(4);
    private static final Random RANDOM = new Random();

    static {
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        serviceInstances.add(new DefaultServiceInstance("serviceA-8081", "serviceA", "127.0.0.1", 8081, false));
        serviceInstances.add(new DefaultServiceInstance("serviceA-8083", "serviceA", "127.0.0.1", 8083, false));
        SERVICE_INSTANCES.put("serviceA", serviceInstances);
    }

    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        List<ServiceInstance> serviceInstances = SERVICE_INSTANCES.get(serviceId);
        if (serviceInstances == null || serviceInstances.isEmpty()) {
            return null;
        }
        return serviceInstances.get(RANDOM.nextInt(serviceInstances.size()));
    }
}
