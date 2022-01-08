package com.nanomt88.demo.dubbo.sample;


import com.alibaba.fastjson.JSON;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MiddleServiceImpl implements IMiddleService{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IMyService demoService2;

    @Override
    public Order middleRpc(User user) {
        log.info("user info : [{}]" , user);
        log.info("RpcContext ObjectAttachments : [{}]" , JSON.toJSONString(RpcContext.getContext().getObjectAttachments()));
        try {
            log.info("sleep 1500ms");
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return demoService2.getUser(user);
    }

}
