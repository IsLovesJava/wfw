## 微服务DEMO

### NACOS

- 安装运行
  - 下载启动 https://nacos.io/docs/latest/quickstart/quick-start/
  - ./bin/startup.cmd -m standalone
  - 打开管理页面 http://127.0.0.1:8848/nacos/index.html
- spring集成
  - 引入依赖 spring-cloud-starter-alibaba-nacos-discovery
  - 添加配置
    - spring.application.name=serviceB
    - spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
    - 启动类添加注解 @EnableDiscoveryClient
- 原理
  - ...

### FEIGN

- spring集成
  - 引入依赖 spring-cloud-starter-openfeign；spring-cloud-starter-loadbalancer 
  - 添加配置
    - 启动类添加注解 @EnableFeignClients
  - 添加接口，使用注解@FeignClient(value = "${serviceName}")
  - 添加方法，使用注解@GetMapping("uri")指向服务提供方的接口
- 原理
  - @FeignClient声明的接口会使用动态代理生成Bean，注册在Spring以供使用,ReflectiveFeign#newInstance()
  - Bean的方法执行会由动态代理方法实现，实际执行为发送http请求,服务提供方的IP端口由服务发现提供,FeignBlockingLoadBalancerClient#execute()
  - 执行时会调用负载均衡寻找可用的服务，ServiceInstance instance = this.loadBalancerClient.choose(serviceId, lbRequest);
  - 不添加其他组件提供服务发现能力时无法执行
  - 可继承 BlockingLoadBalancerClient 类,@Bean注册到容器，自定义服务发现。
  - 可集成 Nacos，实现服务发现。NacosServiceDiscovery#getInstances()

### RocketMQ
- 安装运行 
  - 下载启动 https://rocketmq.apache.org/zh/docs/quickStart/01quickstart/
    - 启动nameServer ./bin/mqnamesrv
    - 启动broker ./bin/mqbroker -n localhost:9876
- spring集成
  - 引入依赖 rocketmq-client；rocketmq-common
  - 添加配置
    - 生产者 groupName；instanceName；namesrvAddr；maxMessageSize；sendMsgTimeout；retryTimesWhenSendFailed
    - 消费者 groupName；topic；instanceName；namesrvAddr；cousumeThreadMin
  - 注册Bean
    - 生产者 DefaultMQProducer，设置各项配置，调用start方法，MQProducerConfig
    - 消费者 DefaultMQPushConsumer，设置各项配置，订阅指定的topic，调用start方法，MQConsumerConfig