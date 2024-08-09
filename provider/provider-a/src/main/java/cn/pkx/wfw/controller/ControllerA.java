package cn.pkx.wfw.controller;

import cn.pkx.wfw.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerA {

    @Value("${server.port}")
    private int port;

    @GetMapping("/a/m1")
    public String m1() {
        return UUIDUtils.get() + "-ControllerA#m1:" + port;
    }

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }
}
