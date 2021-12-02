package com.nanomt88.demo.dubbo.generic;

import com.nanomt88.demo.dubbo.sample.IMyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TimeoutTest {

    @Autowired
    IMyService myService;

    @Test
    public void timeoutTest(){
        //设置超时时间
        String timeout = "4000" ;
        RpcContext.getContext().setObjectAttachment("timeout",timeout);
        log.info("set rpc : [{}] timeout to :[{}]" ,IMyService.class.getName(), timeout);
        myService.sayHello("张44");
    }
}
