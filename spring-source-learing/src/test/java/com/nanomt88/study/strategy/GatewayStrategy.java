package com.nanomt88.study.strategy;

import com.nanomt88.study.ObpRequest;
import com.nanomt88.study.ObpResponse;

/**
 * 网关执行策略
 *
 * @author nanomt88@gmail.com
 * @create 2020-02-14 16:29
 **/
public interface GatewayStrategy {

    /**
     * 策略执行链路
     * @param strategy
     */
    void nextStrategy(GatewayStrategy strategy);

    /**
     * 执行网关检查策略
     * @param req
     * @param resp
     */
    void process(ObpRequest req, ObpResponse resp);
}
