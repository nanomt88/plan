package com.nanomt88.demo.gateway.component;


import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;

public interface ITcComponent {

    /**
     * 组件处理逻辑
     * @param request
     * @param response
     */
    void doProcess(TcRequest request, TcResponse response);
}
