package cn.pkx.wfw.controller;

import cn.pkx.wfw.feign.ServiceA;
import cn.pkx.wfw.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerB {

    @Autowired
    ServiceA serviceA;

    @GetMapping("/b/m1")
    public String m1() {
        return UUIDUtils.get() + "-ControllerA#m1 " + " call serviceA:" + serviceA.m1();
    }

    @GetMapping("/b/random/error")
    public String randomError() {
        return UUIDUtils.get() + "-ControllerA#randomError " + " call serviceA:" + serviceA.randomError();
    }
}
