package com.nanomt88.demo.dubbo.generic;

import org.apache.dubbo.common.Constants;
import com.nanomt88.demo.dubbo.sample.IMyService;
import com.nanomt88.demo.dubbo.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class MyGenericServiceImplTest {

    @Autowired
    private IGenericService genericService;
    @Autowired
    IMyService myService;

    /**
     * 为了启动不失败，需要再启动参数中添加：dubbo.protocol.host=192.168.1.112
     */
    @Test
    void invokeUser() {
        System.getProperties().setProperty(Constants.DUBBO_IP_TO_REGISTRY,"192.168.1.112");
        User user = new User();
        user.setId("100");
        user.setName("张三");
        user.setAccount("zhangshan102");
        genericService.invokeUser(user);
    }

    @Test
    void invokeUser2() {
        rpcMyService();
        User user = new User();
        user.setId("100");
        user.setName("张三");
        user.setAccount("zhangshan102");
        Object sayHello = DubboServcieClientFactory.getInstance().genericInvoke(IMyService.class.getName(), "sayHello", user);
        log.info("结果：[{}]" , sayHello);
    }


    @Test
    void rpcMyService() {
        String hello = myService.sayHello("world"); // 执行远程方法
        log.info("遠程調用返回： {}", hello ); // 显示调用结果
    }
}