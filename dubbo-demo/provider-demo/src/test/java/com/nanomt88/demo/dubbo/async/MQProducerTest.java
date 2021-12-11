package com.nanomt88.demo.dubbo.async;

import com.alibaba.fastjson.JSONObject;
import com.nanomt88.demo.dubbo.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MQProducerTest {

    @Autowired
    private Environment env;
    @Autowired
    MQProducer mqProducer;


    @Test
    public void sendMessage() throws IOException {

        User user = new User();
        user.setRequestNo(UUID.randomUUID().toString());
        user.setAccount("xiaoming123");
        user.setId("123");
        user.setName("小明");
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

        System.in.read();
    }
}