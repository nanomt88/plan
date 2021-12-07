package com.nanomt88.demo.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.nanomt88.demo.*"})
@ImportResource(locations = "classpath:spring-dubbo.xml")
public class SpringBootProviderDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootProviderDemoApplication.class, args);
	}

}
