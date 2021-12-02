package com.nanomt88.demo.dubbo.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyServiceImpl implements IMyService{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String sayHello(String name){
        try {
            log.info("sleep 3000ms");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("sayHello to name : [{}]", name);
        return name + " + hello !!!";
    }

    @Override
    public Order getUser(User user) {
        log.info("user info : [{}]" , user);

        try {
            log.info("sleep 3000ms");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Order order = new Order();
        if(user != null) {
            order.setId("order-" + user.getId());
            order.setUser(user);
            order.setOrderTitle("这是" + user.getName() + "的订单");
            order.setUserId(user.getId());
            order.setUserName(user.getName());
        }
        return order;
    }
}
