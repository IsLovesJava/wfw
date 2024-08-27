package cn.pkx.wfw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Map;

@Slf4j
@RestController()
public class BaseControllerB {
    private static final String baseHtml = """
            <head>
                <title>Base Page</title>
            </head>

            <style>
                pre{
                    font-family: Jetbrains Mono;
                    font-size: 20px
                }
                .a-div{
                    margin-top:10px
                }
            </style>

            <body>
            ${body}
            </body>""";

    private static final String divHtml = "<div class=\"a-div\"><a href=\"http://${ip}:#{port}${uri}\">${uri}</a></div>";

    private static String fixHtml = null;

    @Autowired
    Environment environment;

    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping("/b")
    public String base() {
        if (fixHtml != null) {
            return fixHtml;
        }
        String port = environment.getProperty("server.port", "8080");
        String host = environment.getProperty("server.address", "localhost");

        StringBuilder stringBuilder = new StringBuilder();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (RequestMappingInfo mappingInfo : handlerMethods.keySet()) {
            if (mappingInfo.getPathPatternsCondition() != null) {
                PathPattern firstPattern = mappingInfo.getPathPatternsCondition().getFirstPattern();
                String uri = firstPattern.toString();
                stringBuilder.append(divHtml.replace("${ip}", host).replace("#{port}", port).replace("${uri}", uri)).append("\n");
            } else {
                break;
            }
        }

        fixHtml = baseHtml.replace("${body}", stringBuilder.toString());

        return fixHtml;
    }

}
