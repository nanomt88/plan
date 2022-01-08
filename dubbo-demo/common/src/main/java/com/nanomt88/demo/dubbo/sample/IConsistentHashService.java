package com.nanomt88.demo.dubbo.sample;


public interface IConsistentHashService {

    /**
     *  一致性hash测试 方法
     * @param user
     * @return
     */
    Order asyncToSync(String requestNo,User user);

    /**
     * 异步回调通知
     * @param requestNo 请求流水号，通知的时候根据这个进行一致性hash取值
     * @param order
     */
    void asyncNotify(String requestNo, Order order);
}
