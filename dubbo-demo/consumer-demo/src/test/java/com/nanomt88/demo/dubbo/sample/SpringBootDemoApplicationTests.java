package  com.nanomt88.demo.dubbo.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringBootDemoApplicationTests {

	@Autowired
	IMyService myService;


	@Test
	void rpcMyService() {
		String hello = myService.sayHello("world"); // 执行远程方法
		log.info("遠程調用返回： {}", hello ); // 显示调用结果
	}

}
