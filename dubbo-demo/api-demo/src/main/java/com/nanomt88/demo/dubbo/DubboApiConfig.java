package com.nanomt88.demo.dubbo;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * dubbo 消费端配置
 *
 * @author nanomt88@gmail.com
 * @create 2021-11-28 18:34
 **/
//@Configuration
//@ComponentScan(basePackages = {"com.nanomt88.demo.dubbo"})
public class DubboApiConfig {

//    @Bean
//    public ApplicationConfig applicationConfig() {
//        ApplicationConfig applicationConfig = new ApplicationConfig();
//        applicationConfig.setId("consumer-demo-app");
//        applicationConfig.setName("consumer-demo-app");
//        return applicationConfig;
//    }
//
//    @Bean
//    public RegistryConfig registryConfig() {
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setAddress("zookeeper://10.0.20.121:2181?backup=10.0.20.131:2181,10.0.20.132:2181,10.0.20.133:2181");
//        registryConfig.setCheck(false);
//        return registryConfig;
//    }
//
//    @Bean
//    public MonitorConfig monitorConfig() {
//        MonitorConfig monitorConfig = new MonitorConfig();
//        monitorConfig.setProtocol("registry");
//        return monitorConfig;
//    }
}
