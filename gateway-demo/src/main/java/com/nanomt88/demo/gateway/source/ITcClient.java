package com.nanomt88.demo.gateway.source;

import com.nanomt88.demo.gateway.common.TcRequest;
import com.nanomt88.demo.gateway.common.TcResponse;
import com.nanomt88.demo.gateway.component.ITcComponent;

/**
 * 网关调用远程服务的客户端
 *
 */
public interface ITcClient {

    /**
     * 调用远程服务
     * @param request
     * @param response
     */
    void doInvoke(TcRequest request, TcResponse response);
}
