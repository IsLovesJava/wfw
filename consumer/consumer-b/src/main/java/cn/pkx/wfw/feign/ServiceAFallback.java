package cn.pkx.wfw.feign;

import org.springframework.stereotype.Component;

@Component
public class ServiceAFallback implements ServiceA {

    @Override
    public String m1() {
        return "call serviceA error";
    }

    @Override
    public String randomError() {
        return "call serviceA error";
    }
}
