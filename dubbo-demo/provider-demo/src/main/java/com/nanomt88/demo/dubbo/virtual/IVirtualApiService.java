package com.nanomt88.demo.dubbo.virtual;

import com.nanomt88.demo.dubbo.sample.Order;
import com.nanomt88.demo.dubbo.sample.User;

/**
 * 测试dubbo反射的虚拟接口
 */
public interface IVirtualApiService {

    Order getOrderByUser(User user);
}
