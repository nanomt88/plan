package com.nanomt88.demo.dubbo.sample;

import com.nanomt88.demo.dubbo.async.AsyncTaskManager;
import com.nanomt88.demo.dubbo.async.AsyncTaskMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MyServiceImpl implements IMyService{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AsyncTaskMessage asyncTaskMessage;



    @Override
    public String sayHello(String name){
        try {
            log.info("sleep 2000ms");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        if(AsyncTaskManager.containsKey(user.getRequestNo())){
            log.error("重复的请求：[{}]", user);
            return null;
        }

        //异步转同步，在这里等待10秒
        Order order = AsyncTaskManager.wait10s(user.getRequestNo(), () -> {
            //模拟发送
            log.info("requestNo:[{}]添加成功，发送通知", user.getRequestNo());
            asyncTaskMessage.submitNotify(user);
            return true;
        });
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
