package com.nanomt88.demo.dubbo.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanomt88.demo.dubbo.sample.Order;
import com.nanomt88.demo.dubbo.sample.User;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author TCDD
 */
//@Service
public class ConsumerMessageListenerImpl implements ConsumerMessageListener {

    Logger logger = LoggerFactory.getLogger(ConsumerMessageListener.class);


    @Override
    public boolean onMessage(List<MessageExt> messages, ConsumeConcurrentlyContext Context) throws Exception {

        for (MessageExt msg : messages) {
            logger.info("收到消息：{}, msg：{}", msg.getKeys(), messages);
            if (!handler(msg)) {
                return false;
            }
        }
        return true;
    }

    private boolean handler(MessageExt msg) throws Exception {
        try {

            String msgBody = new String(msg.getBody(), "UTF-8");
            String keys = msg.getKeys();
            logger.info("异步通知服务收到消息, keys : [{}], body : [{}]", keys, msgBody);

            User user = JSON.parseObject(msgBody, User.class);
            assert user != null ;

            AsyncTaskManager.notify(user.getRequestNo(), coverUserToOrder(user));

            logger.info("getReconsumeTimes : [{}]", msg.getReconsumeTimes());
            if(msg.getReconsumeTimes()< 1){
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
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
