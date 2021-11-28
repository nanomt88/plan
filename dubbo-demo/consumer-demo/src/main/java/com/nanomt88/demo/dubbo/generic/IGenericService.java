package com.nanomt88.demo.dubbo.generic;

import com.nanomt88.demo.dubbo.sample.User;

/**
 * @author nanomt88@gmail.com
 * @create 2021-11-27 15:44
 **/
public interface IGenericService {
    /**
     * 最最最简单的泛化调用的例子
     * @param user
     */
    void  invokeUser(User user);
}
