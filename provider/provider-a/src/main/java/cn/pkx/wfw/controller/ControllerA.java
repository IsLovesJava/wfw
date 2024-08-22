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
    @Value("${config.s1:null}")
    private String s1;

    @Autowired
    DefaultMQProducer mqProducer;

    @GetMapping("/a/m1")
    public String m1() {
        System.out.println("--config.s1--" + s1);
        return UUIDUtils.get() + "-ControllerA#m1:" + port;
    }

    @GetMapping("/a/random/error")
    public String randomError() {
        if (Math.random() > 0.5) {
            throw new RuntimeException("random");
        }
        return UUIDUtils.get() + "-ControllerA#m1:" + port;
    }

    @GetMapping("/a/send1")
    public String send1() {
        Message msg = new Message();
        msg.setTopic("wfw");
        msg.setTags("default");
        msg.setInstanceId("controllerA");
        msg.setBody("---------message-----------A".getBytes());
        try {
            mqProducer.send(msg);
            return "send message success";
        } catch (Exception e) {
            log.error("send message error", e);
            return "send message error";
        }
    }
}
