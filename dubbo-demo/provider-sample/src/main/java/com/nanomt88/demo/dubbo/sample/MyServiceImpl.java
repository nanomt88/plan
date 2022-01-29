package com.nanomt88.demo.dubbo.sample;


import com.nanomt88.demo.dubbo.registry.RegistryServerSync;
import org.apache.dubbo.registry.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class MyServiceImpl implements IMyService{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RegistryService registryService;

    @Autowired
    private RegistryServerSync registryServerSync;

    @Override
    public String sayHello(String name){
        try {
            log.info("sleep 3500ms");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //尝试卸载服务
        registryServerSync.unregisterProvider(IMiddleService.class);
        log.info("sayHello to name : [{}]", name);
        return name + " + hello !!!";
    }

    @Override
    public Order asyncToSync(User user){
        log.info("user info : [{}]" , user);

        try {
            log.info("sleep 100ms");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //异步转同步，在这里等待10秒
        Order order = Order.builder().user(user).id(user.getId()).userName(user.getName()).build();
        log.info("获取到异步通知了, order:[{}]",order);
        return order;
    }

    @Override
    public Order getUser(User user) {
        log.info("user info : [{}]" , user);

        try {
            log.info("sleep 1500ms");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return coverUserToOrder(user);
    }

    private Order coverUserToOrder(User user){
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
