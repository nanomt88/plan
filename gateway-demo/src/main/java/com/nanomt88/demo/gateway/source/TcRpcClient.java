package com.nanomt88.demo.gateway.source;

import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class TcRpcClient implements ITcClient{
    @Override
    public void doInvoke(TcRequest request, TcResponse response) {
        //模拟dubbo调用
        log.info("rpc invoke start, request: [{}]", request);
        try {
            Thread.sleep(new Random().nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response.getData().put("responseCode", "000000000");
        response.getData().put("responseMessage", "成功");
        response.getData().putAll(request.getData());
        log.info("rpc invoke end, request: [{}]", response);
    }
}
