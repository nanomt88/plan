package com.nanomt88.demo.dubbo;

import com.nanomt88.demo.dubbo.generic.DubboServcieClientFactory;
import com.nanomt88.demo.dubbo.sample.IMyService;
import com.nanomt88.demo.dubbo.sample.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.nanomt88.demo"})
@ImportResource(locations = "classpath:spring-dubbo.xml")
public class SpringBootConsumerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootConsumerDemoApplication.class, args);
	}

}
