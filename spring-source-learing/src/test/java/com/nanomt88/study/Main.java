package com.nanomt88.study;

import com.nanomt88.study.strategy.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nanomt88@gmail.com
 * @create 2020-02-15 12:39
 **/
public class Main {

    GatewayStrategy strategy1 = new AppIdRateLimiterStrategy();
    GatewayStrategy strategy2 = new AppIdRateLimiterStrategyChain();
    GatewayStrategy strategy3 = new DecryptBizContentStrategy();
    GatewayStrategy strategy4 = new DsbEnableStrategy();
    GatewayStrategy strategy5 = new EncryptBizContentStrategy();
    GatewayStrategy strategy6 = new ParamsMappingStrategy();
    GatewayStrategy strategy7 = new IpBlackListStrategy();
    GatewayStrategy strategy8 = new IpWhiteListStrategy();
    GatewayStrategy strategy9 = new MethodRateLimiterStrategy();
    GatewayStrategy strategy10 = new RemoterInvokeStrategy();
    GatewayStrategy strategy11 = new SignResponseStrategy();
    GatewayStrategy strategy12 = new ValidAppBlackListStrategy();
    GatewayStrategy strategy13 = new ValidAppIdStrategy();
    GatewayStrategy strategy14 = new ValidAppWhiteListStrategy();
    GatewayStrategy strategy15 = new ValidMethodStrategy();
    GatewayStrategy strategy16 = new ValidObpSessionStrategy();
    GatewayStrategy strategy17 = new ValidParamsStrategy();
    GatewayStrategy strategy18 = new ValidSignRequestStrategy();
    GatewayStrategy strategy19 = new ValidTimestampStrategy();
    GatewayStrategy strategy20 = new ValidUcSessionStrategy();
    GatewayStrategy strategy21 = new WriteCookieStrategy();


    public static void main(String[] args) {
        Main main = new Main();
        long time1 = System.currentTimeMillis();
        for(int i =0; i< 1000000; i++){
            main.test();
        }
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time1);
    }


    public  void test() {



        List<GatewayStrategy> strategies = new ArrayList<>();
        strategies.add(strategy1);
        strategies.add(strategy2);
        strategies.add(strategy3);
        strategies.add(strategy4);
        strategies.add(strategy5);
        strategies.add(strategy6);
        strategies.add(strategy7);
        strategies.add(strategy8);
        strategies.add(strategy9);
        strategies.add(strategy10);
        strategies.add(strategy11);
        strategies.add(strategy12);
        strategies.add(strategy13);
        strategies.add(strategy14);
        strategies.add(strategy15);
        strategies.add(strategy16);
        strategies.add(strategy17);
        strategies.add(strategy18);
        strategies.add(strategy19);
        strategies.add(strategy20);
        strategies.add(strategy21);

        for(int i =0; i< strategies.size()-1; i++){
            strategies.get(i).nextStrategy(strategies.get(i+1));
        }
        strategies.get(0).process(null, null);
    }
}
