package cn.pkx.job;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.pkx.wfw.utils.UUIDUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerA {
    @Scheduled(cron = "0/5 * * * * *")
    public void sendHttpRequest() {
        String url = "http://127.0.0.1:18090/test/normal/m1";
        String body = "**request**" + UUIDUtils.get();

        System.out.println(body);
        HttpRequest httpRequest = HttpRequest.post(url).timeout(1000).body(body);
        HttpResponse execute = httpRequest.execute();
        execute.close();
        String response = execute.body();
        System.out.println(response);
        System.out.println();
    }
}
