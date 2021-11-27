package com.nanomt88.demo.dubbo.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyServiceImpl implements IMyService{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String sayHello(String name){
        log.info("sayHello to name : [{}]", name);
        return name + " + hello !!!";
    }

    @Override
    public Order sayHello(User user) {
        log.info("user info : [{}]" , user);
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
