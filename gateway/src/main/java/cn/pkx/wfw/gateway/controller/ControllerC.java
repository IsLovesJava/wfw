package cn.pkx.wfw.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintStream;
import java.util.Date;

@RestController
public class ControllerC {
    @GetMapping("/c/m1")
    public String m1() {
        return "controllerC#m1" + new Date();
    }
}
