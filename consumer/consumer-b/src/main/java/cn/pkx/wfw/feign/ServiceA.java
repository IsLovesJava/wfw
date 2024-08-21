package cn.pkx.wfw.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "serviceA",fallback = ServiceAFallback.class)
public interface ServiceA {
    @GetMapping("/a/m1")
    String m1();
}
