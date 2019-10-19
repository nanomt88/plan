package com.nanomt88.demo.messageconverter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author nanomt88@gmail.com
 * @create 2019-07-22 22:55
 **/
@Configuration
public class SpringAnnotationMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.register(SpringAnnotationMain.class);
        context.refresh();
        System.out.println(context.getBean(SpringAnnotationMain.class));
    }
}
