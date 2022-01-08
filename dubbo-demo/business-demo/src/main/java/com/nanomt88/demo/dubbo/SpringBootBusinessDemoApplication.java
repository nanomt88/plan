package com.nanomt88.demo.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.nanomt88.demo.*"})
@ImportResource(locations = "classpath:spring-dubbo.xml")
public class SpringBootBusinessDemoApplication {

	public static void main(String[] args) {

		// 启动多个的时候，需要设置以下参数
		// --server.port=9002 --dubbo.protocol.port=20882 --dubbo.protocol.name=dubbo
		SpringApplication.run(SpringBootBusinessDemoApplication.class, args);
	}

}
