package com.nanomt88.demo.dubbo.sample;

/**
 * TODO
 *
 * @author nanomt88@gmail.com
 * @create 2022/1/29 11:59
 **/
public class MiddleServiceImpl implements IMiddleService{
    @Override
    public Order middleRpc(User user) {
        System.out.println("middleRpc : " + user);

        return new Order();
    }
}
