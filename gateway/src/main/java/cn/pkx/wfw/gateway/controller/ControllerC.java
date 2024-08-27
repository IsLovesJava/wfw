package cn.pkx.wfw.gateway.controller;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class ControllerC{
    ConfigService configService;

    @Autowired
    public void setConfigService(NacosConfigManager manager) {
        this.configService = manager.getConfigService();
    }

    @Value("${spring.application.name}")
    public String appName;

    @Value("${spring.profiles.active}")
    public String activeProfile;

    @Value("${spring.cloud.nacos.config.file-extension}")
    public String fileExtension;

    public String info;

    @Value("${config.info:null-default}")
    public void setInfo(String info) {
        log.error("stack trace:", new Exception());
        this.info = info;
    }

    @GetMapping("/c/m1")
    public String m1() {
        String htmlPage = null;
        try {
            htmlPage = configService.getConfig("basepage.html", "DEFAULT_GROUP", 1000);
        } catch (NacosException e) {
            log.error("NacosException:", e);
            htmlPage = "NacosException";
        }
        String body = "controllerC#m1" + new Date() + "----" + info;
        return htmlPage.replace("${body}", body);
    }
}
