package com.nanomt88.demo.dubbo.virtual;


import com.nanomt88.demo.dubbo.sample.Order;
import com.nanomt88.demo.dubbo.sample.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 自定义虚拟接口实现
 */
@Service
@Slf4j
public class VirtualApiServiceImpl implements IVirtualApiService {

    @VirtualDubboApi(value = "getOrderByUser")
    @Override
    public Order getOrderByUser(User user) {
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
