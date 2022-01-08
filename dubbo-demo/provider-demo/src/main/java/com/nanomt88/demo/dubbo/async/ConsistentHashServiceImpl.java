package com.nanomt88.demo.dubbo.async;

import com.nanomt88.demo.dubbo.sample.IConsistentHashService;
import com.nanomt88.demo.dubbo.sample.IMyService;
import com.nanomt88.demo.dubbo.sample.Order;
import com.nanomt88.demo.dubbo.sample.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsistentHashServiceImpl implements IConsistentHashService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AsyncTaskMessage asyncTaskMessage;


    @Override
    public Order asyncToSync(String requestNo,User user) {
        log.info("user info : [{}]" , user);

        try {
            log.info("sleep 100ms");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(AsyncTaskManager.containsKey(requestNo)){
            log.error("重复的请求：[{}]", user);
            return null;
        }

        //异步转同步，在这里等待10秒
        Order order = AsyncTaskManager.wait10s(requestNo, () -> {
            //模拟发送
            log.info("requestNo:[{}]添加成功，发送通知", requestNo);
            asyncTaskMessage.submitNotify(user);
            return true;
        });
        log.info("获取到异步通知了, order:[{}]",order);
        return order;
    }

    @Override
    public void asyncNotify(String requestNo, Order order) {
        assert order !=null;
        AsyncTaskManager.notify(requestNo, order);

        log.info("异步回调通知 : [{}]", order);

    }
}
