package cn.pkx.config;


import cn.pkx.annotation.ConditionalOnAaaBbb;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class C1 {
    @Bean
    @ConditionalOnAaaBbb
    public Object aaaBbb() {
        System.out.println("----------------aaaBbb-------------");
        return new Object();
    }

    @Bean
    @Conditional(FalseConditional.class)
    public Object alwaysFalse() {
        System.out.println("----------------alwaysFalse-------------");
        return new Object();
    }

    @Bean
    public Object alwaysExec() {
        System.out.println("----------------alwaysExec-------------");
        return new Object();
    }
}
