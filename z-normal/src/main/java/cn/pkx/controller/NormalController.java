package cn.pkx.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NormalController {
    @PostMapping("/test/normal/m1")
    private String m1(@RequestBody String s) {
        return s.replace("request","response");
    }
}
