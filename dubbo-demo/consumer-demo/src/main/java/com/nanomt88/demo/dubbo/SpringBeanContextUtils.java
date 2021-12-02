package com.nanomt88.demo.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author nanomt88@gmail.com
 * @create 2021-11-27 17:08
 **/
@Component
@Slf4j
public class SpringBeanContextUtils implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT ;

    public static final <T> T getBean(String beanName){
        if(APPLICATION_CONTEXT ==null){
            log.error("ApplicationContext is null");
            return null;
        }else if( !APPLICATION_CONTEXT.containsBean(beanName)){
            log.warn("ApplicationContext can not find bean by name : [{}]", beanName);
            return null;
        }
        return (T) APPLICATION_CONTEXT.getBean(beanName);
    }

    public static final <T> T getBean(Class<T> clazz){
        if(APPLICATION_CONTEXT ==null){
            log.error("ApplicationContext is null");
            return null;
        }
        return (T) APPLICATION_CONTEXT.getBean(clazz);
    }

    public static final <T> T getBean(String beanName, Class<T> clazz){
        if(APPLICATION_CONTEXT ==null){
            log.error("ApplicationContext is null");
            return null;
        }
        return (T) APPLICATION_CONTEXT.getBean(beanName, clazz);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;

//        System.getProperties().setProperty(Constants.DUBBO_IP_TO_REGISTRY,"127.0.0.1");
    }
}
