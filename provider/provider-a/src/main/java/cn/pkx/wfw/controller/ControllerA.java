package cn.pkx.wfw.controller;

import cn.pkx.wfw.mq.RocketMqHelper;
import cn.pkx.wfw.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerA {

    @Value("${server.port}")
    private int port;

    @Autowired
    RocketMqHelper rocketMqHelper;

    @GetMapping("/a/m1")
    public String m1() {
        rocketMqHelper.asyncSend("wfw", MessageBuilder.withPayload("mq-/a/m1").build());
        return UUIDUtils.get() + "-ControllerA#m1:" + port;
    }

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }
}
