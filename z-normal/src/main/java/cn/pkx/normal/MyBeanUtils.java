package cn.pkx.normal;

import cn.pkx.annotation.MyAnnotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MyBeanUtils implements ApplicationContextAware, SmartInitializingSingleton {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(MyAnnotation.class);
        System.out.println(beansWithAnnotation);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
