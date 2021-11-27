package com.nanomt88.study.strategy;

import com.nanomt88.study.ObpRequest;
import com.nanomt88.study.ObpResponse;

/**
 * @author hongxudong123@pingan.com.cn
 * @create 2020-02-14 16:41
 **/
public class SignResponseStrategy implements GatewayStrategy {

    /**
     * 下一个策略
     */
    private GatewayStrategy nextStrategy;

    /**
     * 设置下一个策略
     *
     * @param strategy
     */
    @Override
    public void nextStrategy(GatewayStrategy strategy) {
        nextStrategy = strategy;
    }

    @Override
    public void process(ObpRequest req, ObpResponse resp) {
        //System.out.println(this.getClass().getName());
        if(nextStrategy != null){
            nextStrategy.process(req,resp);
        }
    }

}
