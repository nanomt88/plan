package com.nanomt88.demo.gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author nanomt88@gmail.com
 * @create 2019-07-22 22:55
 **/
@Configuration
public class SpringXmlStarter {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext();
        context.start();
        context.refresh();
        System.out.println(context.getBean(SpringXmlStarter.class));
    }
}
