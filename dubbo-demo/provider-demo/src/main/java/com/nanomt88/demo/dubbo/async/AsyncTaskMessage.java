package com.nanomt88.demo.dubbo.async;

import com.alibaba.fastjson.JSONObject;
import com.nanomt88.demo.dubbo.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class AsyncTaskMessage {

    @Autowired
    MQProducer mqProducer;

    @Autowired
    private Environment env;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,20, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(500));

    public void submitNotify(final User user){
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {

                try {
                    log.info("mock task notify 2000ms");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //模拟收到交易成功通话后，发送通知
                Message message = new Message();
                message.setTopic(env.getProperty("rocketmq.asyncToSync.consumer.topic"));
//        message.setTags("tag");
                message.setKeys(UUID.randomUUID().toString());
                message.setBody(JSONObject.toJSONBytes(user));
                try {
                    mqProducer.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("模拟 收到外部成功通知，并发送系统 : [{}]... ", message);
            }
        });
    }
}
