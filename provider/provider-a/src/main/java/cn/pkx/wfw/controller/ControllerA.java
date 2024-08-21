package cn.pkx.wfw.controller;

import cn.pkx.wfw.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ControllerA {

    @Value("${server.port}")
    private int port;

    @Autowired
    DefaultMQProducer mqProducer;

    @GetMapping("/a/m1")
    public String m1() {
        Message msg = new Message();
        msg.setTopic("wfw");
        msg.setTags("default");
        msg.setInstanceId("controllerA");
        msg.setBody("---------message-----------A".getBytes());

        try {
            mqProducer.send(msg);
            log.error("send message success");
        } catch (Exception e) {
            log.error("send message error", e);
        }
        if (Math.random() > 0.5) {
            throw new RuntimeException("random");
        }
        return UUIDUtils.get() + "-ControllerA#m1:" + port;
    }

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }
}
