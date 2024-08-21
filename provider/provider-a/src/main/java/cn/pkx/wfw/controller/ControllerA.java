package cn.pkx.wfw.controller;

import cn.pkx.wfw.utils.UUIDUtils;
import com.alibaba.cloud.nacos.discovery.configclient.NacosConfigServerAutoConfiguration;
import com.alibaba.nacos.client.config.NacosConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class ControllerA {

    @Value("${server.port}")
    private int port;

    @Autowired
    DefaultMQProducer mqProducer;
    @Autowired
    NacosConfigService nacosConfigService;

    @GetMapping("/a/m1")
    public String m1() {
        Message msg = new Message();
        msg.setTopic("wfw");
        msg.setTags("default");
        msg.setInstanceId("controllerA");
        try {
            String config = nacosConfigService.getConfig("wfw-serviceA-msg", "wfw-demo", 1000);
            msg.setBody(config.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("get nacos config error", e);
            msg.setBody("get nacos config error".getBytes(StandardCharsets.UTF_8));
        }

        try {
            mqProducer.send(msg);
            log.error("send message success");
        } catch (Exception e) {
            log.error("send message error", e);
        }
        return UUIDUtils.get() + "-ControllerA#m1:" + port;
    }

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }
}
