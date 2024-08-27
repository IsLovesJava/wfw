package cn.pkx.wfw.aspect;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BasePageFill {
    ConfigService configService;

    @Autowired
    public void setConfigService(NacosConfigManager manager) {
        this.configService = manager.getConfigService();
    }

    @Pointcut("@annotation(cn.pkx.wfw.aspect.HtmlBody)")
    public void setHtmlBody() {
    }

    public String fill(String s) {
        String htmlPage;
        try {
            htmlPage = configService.getConfig("basepage.html", "DEFAULT_GROUP", 1000);
        } catch (NacosException e) {
            log.error("NacosException:", e);
            htmlPage = "NacosException:" + e.getErrMsg();
        }
        return htmlPage.replace("${body}", s);
    }

    @Around("setHtmlBody()")
    public Object afterReturning(ProceedingJoinPoint point) throws Throwable {
        Object proceed = point.proceed();
        if (proceed instanceof String s) {
            return fill(s);
        }
        return proceed;
    }
}
