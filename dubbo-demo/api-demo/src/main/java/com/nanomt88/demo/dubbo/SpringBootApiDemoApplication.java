package com.nanomt88.demo.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.nanomt88.demo.dubbo.*"})
//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@ServletComponentScan(basePackages = {"com.nanomt88.demo.dubbo"})
public class SpringBootApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApiDemoApplication.class, args);
	}

}
