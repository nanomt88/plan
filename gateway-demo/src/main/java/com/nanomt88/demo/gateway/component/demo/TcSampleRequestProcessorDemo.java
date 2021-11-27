package com.nanomt88.demo.gateway.component.demo;

import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import com.nanomt88.demo.gateway.component.ITcComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcSampleRequestProcessorDemo implements ITcComponent {


    @Override
    public void doProcess(TcRequest request, TcResponse response) {
        log.info("invoke, request:[{}]", request);
        request.getData().put("AppId", "app123");

    }
}
