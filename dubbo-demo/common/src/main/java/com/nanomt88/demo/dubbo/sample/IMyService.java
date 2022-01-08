package com.nanomt88.demo.dubbo.sample;

import org.springframework.stereotype.Component;


public interface IMyService {

    String sayHello(String name);

    /**
     * 异步转同步示例
     * @param name
     * @return
     */
    Order asyncToSync(User name);


    Order getUser(User name);
}
