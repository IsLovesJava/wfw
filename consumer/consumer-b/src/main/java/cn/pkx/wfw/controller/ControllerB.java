package cn.pkx.wfw.controller;

import cn.pkx.wfw.aspect.HtmlBody;
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
    @HtmlBody
    public String m1() {
        return UUIDUtils.get() + "-ControllerA#m1 " + "\ncall serviceA:" + serviceA.m1();
    }

    @GetMapping("/b/random/error")
    @HtmlBody
    public String randomError() {
        return UUIDUtils.get() + "-ControllerA#randomError " + "\ncall serviceA:" + serviceA.randomError();
    }

    @GetMapping("/b/local/m2")
    @HtmlBody
    public String localM2() {
        return UUIDUtils.get() + "-ControllerA#localM2 ";
    }
}
