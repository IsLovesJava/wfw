## 微服务DEMO

### Windows编译项目
- chcp 65001  CMD/power shell设置UTF-8编码
  - chcp 437  CMD/power shell设置默认编码
- set JAVA_HOME=C:\java\jdk-21.0.2 CMD指定jdk版本
- $env:JAVA_HOME = "C:\java\jdk-21.0.2" power shell指定jdk版本
- mvn clean package -P consumers

### 运行项目
- java -jar consumer-b-1.0.jar --rocketmq.consumer.namesrvAddr=192.168.3.28:9876
- 参数--X.Y.Z=xxx 设置spring参数
- java -jar /software/java-projects/consumer-b-1.0.jar > output.log 2>&1 &

### docker安装
- 参考文档 https://juejin.cn/post/7154437479955693598
- linux机器（需联网），版本 CentOS Linux release 7.9.2009 (Core)
- 配置yum
  - 由于7.9版本不再维护，yum将7.9视为归档版本，无法直接访问默认镜像网址，需要修改镜像网址
  - 修改 vi /etc/yum.repos.d/CentOS-Base.repo，注释停用mirrorlist项，启用baseurl
  - 修改各项baseurl，设值为baseurl=http://vault.centos.org/centos/7.9.2009/os/$basearch/
  - 安装工具 yum install -y yum-utils device-mapper-persistent-data lvm2
  - 设置国内镜像源 yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
  - 刷新缓存 yum makecache fast 
- 安装社区版 docker yum -y install docker-ce
- 安装完成，若拉取镜像缓慢，可修改/etc/docker/daemon.json，设置容器镜像加速，保存后重启docker
- ```json
  {
    "registry-mirrors": [
      "https://gallery.ecr.aws/",
      "https://docker.lmirror.top",
      "https://atomhub.openatom.cn/"
    ]
  }
  ```
- 制作jar程序镜像
  - 编写dockerfile，同一目录下放入jdk-21_linux-x64_bin.tar.gz，consumer-b-1.0.jar
  - ```dockerfile
    FROM centos:centos7.9.2009
    ADD jdk-21_linux-x64_bin.tar.gz /java/
    ADD consumer-b-1.0.jar /java/
    ENV JAVA_HOME /java/jdk-21.0.4
    ENV CLASSPATH .:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar
    EXPOSE 18082
    CMD ["/java/jdk-21.0.4/bin/java","-jar","/java/consumer-b-1.0.jar"]
    ```
  - dockerfile所在目录执行 
  - ```shell
    docker build -t serviceB:latest .
    ```
- 启动容器
  - ```shell
    docker run --name=svb1 svb > /tmp/logs/svb1.out 2>&1 &
    docker run -p 12080:18082 --name=serviceB-1 serviceB > /tmp/logs/serviceB-1-12080.out 2>&1 &
    docker run -p 12081:18082 --name=serviceB-2 serviceB > /tmp/logs/serviceB-1-12081.out 2>&1 &
    ```

### NACOS
- 安装运行
  - 下载启动 https://nacos.io/docs/latest/quickstart/quick-start/
  - ./bin/startup.cmd -m standalone
  - 打开管理页面 http://127.0.0.1:8848/nacos/index.html
- spring集成
  - 引入依赖 spring-cloud-starter-alibaba-nacos-discovery
  - 添加配置
    - ```properties
      spring.application.name=serviceB
      spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848
      ```
    - 启动类添加注解  @EnableDiscoveryClient 
- 服务注册原理
  - NacosServiceRegistryAutoConfiguration#nacosAutoServiceRegistration启动注册事件监听Bean
  - 事件触发NacosAutoServiceRegistration#onApplicationEvent(WebServerInitializedEvent)->start()->register()->
  - NacosServiceRegistry#register()->NacosServiceRegistry#registerInstance()->NamingGrpcClientProxy#registerServiceForEphemeral()
  - 通过gRPC请求注册服务

### Consul
- 安装运行
  - 官网下载 https://developer.hashicorp.com/consul/install
  - windows平台：解压，执行 consul agent -dev
  - 打开管理页面 http://localhost:8500/
- spring集成
  - 引入依赖 spring-cloud-starter-consul-discovery
  - 添加配置
    - ```properties
      spring.cloud.consul.discovery.service-name=consul-a
      spring.cloud.consul.host=127.0.0.1
      spring.cloud.consul.port=8500
      ```
    - 启动类添加注解  @EnableDiscoveryClient
- 服务注册原理
  - ConsulAutoServiceRegistrationListener监听程序启动
  - 事件触发ConsulAutoServiceRegistration#start()->register()->ConsulServiceRegistry#register()
  ->AgentConsulClient#agentServiceRegister(),发送http请求注册服务

### FEIGN
- spring集成
  - 引入依赖 spring-cloud-starter-openfeign;spring-cloud-starter-loadbalancer 
  - 添加配置
    - 启动类添加注解 @EnableFeignClients
  - 添加接口
    - ```java
      import org.springframework.cloud.openfeign.FeignClient;
      import org.springframework.web.bind.annotation.GetMapping;
  
      @FeignClient(value = "serviceA")
      public interface ServiceA {
          @GetMapping("/a/m1")
          String m1();
      }
      ```
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
  - 引入依赖 rocketmq-client;rocketmq-common
  - 添加配置
    - 生产者 groupName;instanceName;namesrvAddr;maxMessageSize;sendMsgTimeout;retryTimesWhenSendFailed
    - 消费者 groupName;topic;instanceName;namesrvAddr;cousumeThreadMin
  - 注册Bean
    - 生产者 DefaultMQProducer，设置各项配置，调用start方法。例：MQProducerConfig
    - 消费者 DefaultMQPushConsumer，设置各项配置，订阅指定的topic，调用start方法。例：MQConsumerConfig

### Sentinel
- 文档
  - 官方文档 https://sentinelguard.io/zh-cn/docs/introduction.html
  - spring cloud 体系 https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel
- feign集成，使用spring-cloud-starter-openfeign
  - 引入依赖，spring-cloud-starter-alibaba-sentinel
  - feign接口注解添加降级实现类@FeignClient(value = "serviceA",fallback = ServiceAFallback.class)
  - ServiceAFallback实现serviceA接口，并为各个方法实现降级处理
- 原理
  - Sentinel实现Feign.Builder,重写动态代理实现，使用SentinelInvocationHandler，补充降级处理逻辑，SentinelFeign#internalBuild#create
  - feign方法执行发生异常时，捕获异常，执行指定的降级方法，SentinelInvocationHandler#invoke