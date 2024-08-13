package cn.pkx.wfw.mq.condition;

import cn.pkx.wfw.mq.annotation.EnableRocketMqConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

@Slf4j
public class EnableConsumerCheck implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if (beanFactory != null) {
            Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(EnableRocketMqConsumer.class);
            if (!beansWithAnnotation.isEmpty()){
                log.info("EnableRocketMqConsumer");
                return true;
            }
            return false;
        }
        return false;
    }
}
